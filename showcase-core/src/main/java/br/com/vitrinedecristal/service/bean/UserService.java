package br.com.vitrinedecristal.service.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.vitrinedecristal.dao.IUserDAO;
import br.com.vitrinedecristal.dto.CreateUserDTO;
import br.com.vitrinedecristal.dto.LoginDTO;
import br.com.vitrinedecristal.dto.UpdateUserPasswordDTO;
import br.com.vitrinedecristal.dto.UserDTO;
import br.com.vitrinedecristal.enums.RoleEnum;
import br.com.vitrinedecristal.enums.UserStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.exception.InvalidPermissionException;
import br.com.vitrinedecristal.exception.UserAlreadyExistsException;
import br.com.vitrinedecristal.mail.IMailSender;
import br.com.vitrinedecristal.mail.MailSender;
import br.com.vitrinedecristal.model.Token;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.security.credential.UserCredentials;
import br.com.vitrinedecristal.security.util.AuthenticationUtils;
import br.com.vitrinedecristal.service.ITokenService;
import br.com.vitrinedecristal.service.IUserLoginService;
import br.com.vitrinedecristal.service.IUserService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.EmailValidator;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.UserVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link User}
 */
public class UserService extends BaseService<Long, User, IUserDAO> implements IUserService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(UserService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ITokenService tokenService;

	@Autowired
	private IUserLoginService userLoginService;

	public UserService(IUserDAO userDAO) {
		super(userDAO);
	}

	@Override
	public List<UserDTO> listUsers() throws BusinessException {
		logger.info("Listando os usuários");
		List<User> users = super.find();
		logger.info("Usuários listados com sucesso");

		return ParserUtil.parse(users, UserDTO.class);
	}

	@Override
	public UserVO getUser(String id) throws BusinessException {
		logger.info("Obtendo usuário pelo id: " + id);

		Long userId = null;
		if (id.equals("@self")) {
			logger.info("Obtendo informações do usuário logado..");
			userId = AuthenticationUtils.getUserId();
			logger.info("Usuário logado resgatado [" + userId + "]");
		} else if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
			throw new InvalidPermissionException();
		}

		if (userId == null) {
			try {
				userId = Long.valueOf(id);
			} catch (Exception e) {
				throw new BusinessException("Não foi possível converter o ID do usuário");
			}
		}

		User user = super.findByPrimaryKey(userId);
		if (user == null) {
			throw new EntityNotFoundException();
		}

		return ParserUtil.parse(user, UserVO.class);
	}

	@Override
	@Transactional
	public UserDTO createUser(CreateUserDTO createUserDTO) throws BusinessException {
		if (createUserDTO == null) {
			throw new IllegalArgumentException("A entidade usuário não pode ser nula.");
		}

		if (StringUtils.isBlank(createUserDTO.getNome())) {
			throw new BusinessException("O campo nome é obrigatório");
		}

		if (StringUtils.isBlank(createUserDTO.getEmail())) {
			throw new BusinessException("O campo e-mail é obrigatório");
		}
		this.validateEmail(createUserDTO.getEmail());

		if (StringUtils.isBlank(createUserDTO.getSenha())) {
			throw new BusinessException("O campo senha é obrigatório");
		}

		User user = ParserUtil.parse(createUserDTO, User.class);
		user.setSenha(this.encryptPassword(createUserDTO.getSenha()));
		user.setStatus(UserStatusEnum.INACTIVE);
		user.setDtAtualizacao(new Date());
		user.setRoles(Arrays.asList(RoleEnum.ROLE_USER));

		logger.info("Criando usuário: " + user);
		User storedUser = super.save(user);
		Token token = this.tokenService.createWelcomeToken(storedUser);
		logger.info("Usuário criado com sucesso!");

		if (EmailValidator.validate(user.getEmail())) {
			IMailSender mailSender = new MailSender();
			mailSender.sendWelcomeMail(user.getNome(), user.getEmail(), token.getHash());
		}

		return ParserUtil.parse(storedUser, UserDTO.class);
	}

	@Override
	@Transactional
	public UserVO updateUser(UserVO userVO) throws BusinessException {
		logger.info("Atualizando usuário: " + userVO);

		if (userVO == null) {
			throw new IllegalArgumentException("A entidade usuário não pode ser nula.");
		}

		// if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !userVO.getId().equals(AuthenticationUtils.getUserId())) {
		// throw new InvalidPermissionException();
		// }

		User storedUser = getDAO().findByPrimaryKey(userVO.getId());
		if (storedUser == null) {
			throw new EntityNotFoundException();
		}

		if (StringUtils.isNotBlank(userVO.getEmail())) {
			storedUser.setEmail(userVO.getEmail());
		}

		// if (AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
		// if (StringUtils.isNotBlank(userVO.getEmail())) {
		// storedUser.setEmail(userVO.getEmail());
		// }
		//
		// if (userVO.getRoles() != null && !userVO.getRoles().isEmpty() && RoleEnum.containsAll(userVO.getRoles())) {
		// storedUser.setRoles(userVO.getRoles());
		// }
		//
		// if (userVO.getClassificacao() != null) {
		// storedUser.setClassificacao(userVO.getClassificacao());
		// }
		// }

		if (StringUtils.isNotBlank(userVO.getNome())) {
			storedUser.setNome(userVO.getNome());
		}

		if (userVO.getSexo() != null) {
			storedUser.setSexo(userVO.getSexo());
		}

		if (StringUtils.isNotBlank(userVO.getTelefone())) {
			storedUser.setTelefone(userVO.getTelefone());
		}

		if (userVO.getDtNascimento() != null) {
			storedUser.setDtNascimento(userVO.getDtNascimento());
		}

		storedUser.setStatus(UserStatusEnum.ACTIVE);
		storedUser.setDtAtualizacao(new Date());
		storedUser = super.save(storedUser);
		logger.info("Usuário atualizado com sucesso!");

		return ParserUtil.parse(storedUser, UserVO.class);
	}

	@Override
	public void removeUser(Long id) throws BusinessException {
		// if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
		// throw new InvalidPermissionException();
		// }

		this.updateStatus(id, UserStatusEnum.INACTIVE);
	}

	@Override
	@Transactional
	public void updateStatus(Long id, UserStatusEnum status) throws BusinessException {
		if (id == null) {
			throw new IllegalArgumentException("O id do usuário não pode ser nulo.");
		}

		if (status == null) {
			throw new IllegalArgumentException("O novo status do usuário não pode ser nulo.");
		}

		logger.info("Alterando status do usuário [" + id + "] para " + status);
		User storedUser = getDAO().findByPrimaryKey(id);
		if (storedUser == null) {
			throw new EntityNotFoundException();
		}

		storedUser.setStatus(status);
		storedUser.setDtAtualizacao(new Date());
		super.save(storedUser);

		logger.info("Status do usuário atualizado com sucesso!");
	}

	@Override
	@Transactional
	public void updatePassword(UpdateUserPasswordDTO updateUserPasswordDTO) throws BusinessException {
		if (updateUserPasswordDTO == null) {
			throw new IllegalArgumentException("A entidade não pode ser nula.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !updateUserPasswordDTO.getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		logger.info("Alterando a senha do usuário [" + updateUserPasswordDTO.getId() + "]");
		User storedUser = getDAO().findByPrimaryKey(updateUserPasswordDTO.getId());
		if (storedUser == null) {
			throw new EntityNotFoundException();
		}

		this.updateUserPassword(updateUserPasswordDTO.getId(), null, updateUserPasswordDTO.getSenha());
	}

	// TODO função para atualizar a classificação do usuário (calcular a média e atualizad na base..)

	@Override
	public void recoveryPassword(String email) throws BusinessException {
		if (StringUtils.isBlank(email)) {
			throw new BusinessException("E-mail informado para a recuperação de senha é inválido.");
		}

		logger.info("Recuperando senha do usuário: " + email);

		User storedUser = null;
		try {
			storedUser = getDAO().findByEmail(email, Arrays.asList(UserStatusEnum.ACTIVE, UserStatusEnum.INCOMPLETE));
		} catch (Exception e) {
			throw new BusinessException("E-mail informado para a recuperação de senha é inválido.");
		}

		Token token = this.tokenService.createTokenForPasswordRecovery(storedUser);
		if (EmailValidator.validate(email)) {
			IMailSender mailSender = new MailSender();
			mailSender.sendRecoveryPasswordMail(storedUser.getNome(), storedUser.getEmail(), token.getHash());
		}

		logger.info("E-mail de recuperação de senha foi enviado com sucesso!");
	}

	@Override
	public void updateForgottenPassword(String tokenHash, String newPassword, Long userId) throws BusinessException {
		Token token = tokenService.validatePasswordForgotToken(tokenHash, userId);
		this.updateUserPassword(token.getUsuario().getId(), null, newPassword);
	}

	@Override
	public UserVO login(LoginDTO loginDTO) {
		if (loginDTO == null) {
			throw new IllegalArgumentException("A entidade usuário não pode ser nula.");
		}

		logger.info("Usuário logando: " + loginDTO.getEmail());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());

		Authentication auth = null;
		try {
			auth = this.authenticationManager.authenticate(token);
			AuthenticationUtils.login(auth);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException("Usuário e/ou senha incorretos.");
		}

		UserVO vo = null;
		if (auth != null && auth.getPrincipal() instanceof UserCredentials) {
			UserCredentials userDetails = (UserCredentials) auth.getPrincipal();

			User user = super.findByPrimaryKey(userDetails.getId());
			if (user == null) {
				throw new EntityNotFoundException("Não foi encontrado nenhum usuário com o e-mail informado.");
			}

			this.userLoginService.saveLastAccess(user);
			vo = ParserUtil.parse(user, UserVO.class);
		}

		return vo;
	}

	/**
	 * Verifica se já existe um usuário com o e-mail informado no parâmetro.
	 * 
	 * @param email o e-mail que será usado na consulta.
	 * @return <tt>true</tt> se o e-mail existe e <tt>false</tt> caso contrário ou se o parâmetro for <code>null</code>.
	 * @throws UserAlreadyExistsException
	 */
	private void validateEmail(String email) throws UserAlreadyExistsException {
		logger.info("Validando se o e-mail já está cadastrado: " + email);
		User filter = new User();
		filter.setEmail(email);

		Boolean alreadyExists = getDAO().count(filter) > 0;
		if (alreadyExists) {
			throw new UserAlreadyExistsException();
		}
	}

	/**
	 * Criptografa uma senha usando o algoritmo do springframework security {@link BCryptPasswordEncoder}.
	 * 
	 * @param password a senha a ser criptografada
	 * @return a senha criptografada
	 */
	private String encryptPassword(String password) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(password);
		return encodedPassword;
	}

	/**
	 * Altera a senha do usuário, caso a senha atual seja informada valida-se se está correta com a da base.
	 * 
	 * @param userId o ID do usuário
	 * @param password a senha atual
	 * @param newPassword a nova senha
	 * @throws BusinessException
	 */
	private void updateUserPassword(Long userId, String password, String newPassword) throws BusinessException {
		if (StringUtils.isBlank(newPassword)) {
			throw new IllegalArgumentException("A nova senha não foi informada.");
		}

		logger.info("Alterando senha atual do usuário para uma nova senha: [" + newPassword + "]");

		User user = super.findByPrimaryKey(userId);
		if (user == null) {
			throw new EntityNotFoundException();
		}

		if (password != null) {
			String encryptedPassword = this.encryptPassword(password);
			if (!encryptedPassword.equals(user.getSenha())) {
				throw new BusinessException("A senha informada para realizar a troca não é válida com a senha cadastrada na base de dados.");
			}
		}

		String newPasswordEncrypted = this.encryptPassword(newPassword);
		if (user.getSenha().equals(newPasswordEncrypted)) {
			throw new BusinessException("A nova senha é igual a senha anterior.");
		}

		user.setSenha(newPasswordEncrypted);
		user.setDtAtualizacao(new Date());
		super.save(user);

		logger.info("Senha alterada com sucesso!");
	}

}
