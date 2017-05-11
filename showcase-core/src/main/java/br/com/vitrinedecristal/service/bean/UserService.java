package br.com.vitrinedecristal.service.bean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.vitrinedecristal.application.ApplicationBeanFactory;
import br.com.vitrinedecristal.dao.IUserDAO;
import br.com.vitrinedecristal.dto.LoginDTO;
import br.com.vitrinedecristal.dto.UserDTO;
import br.com.vitrinedecristal.enums.RoleEnum;
import br.com.vitrinedecristal.enums.UserStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.exception.InvalidPermissionException;
import br.com.vitrinedecristal.exception.UserAlreadyExistsException;
import br.com.vitrinedecristal.model.Token;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.security.credential.UserCredentials;
import br.com.vitrinedecristal.security.util.AuthenticationUtils;
import br.com.vitrinedecristal.service.ITokenService;
import br.com.vitrinedecristal.service.IUserLoginService;
import br.com.vitrinedecristal.service.IUserService;
import br.com.vitrinedecristal.service.base.BaseService;
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
	public UserVO get(String id) throws BusinessException {
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
	public UserDTO createUser(UserVO userVO) throws BusinessException {
		if (userVO == null) {
			throw new IllegalArgumentException("A entidade usuário não pode ser nula.");
		}

		if (StringUtils.isBlank(userVO.getNome())) {
			throw new BusinessException("O campo nome é obrigatório");
		}

		if (StringUtils.isBlank(userVO.getEmail())) {
			throw new BusinessException("O campo e-mail é obrigatório");
		}
		this.validateEmail(userVO.getEmail());

		if (StringUtils.isBlank(userVO.getSenha())) {
			throw new BusinessException("O campo senha é obrigatório");
		}

		userVO.setSenha(this.encryptPassword(userVO.getSenha()));
		userVO.setStatus(UserStatusEnum.INCOMPLETE);
		userVO.setDtAtualizacao(new Date());
		userVO.setRoles(Arrays.asList(RoleEnum.ROLE_USER));

		logger.info("Criando usuário: " + userVO);
		User user = super.save(ParserUtil.parse(userVO, User.class));
		logger.info("Usuário criado com sucesso!");

		return ParserUtil.parse(user, UserDTO.class);
	}

	@Override
	@Transactional
	public UserDTO updateUser(UserVO userVO) throws BusinessException {
		logger.info("Atualizando usuário: " + userVO);

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !userVO.getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		if (userVO == null) {
			throw new IllegalArgumentException("A entidade usuário não pode ser nula.");
		}

		User storedUser = getDAO().findByPrimaryKey(userVO.getId());
		if (storedUser == null) {
			throw new EntityNotFoundException();
		}

		if (AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
			if (StringUtils.isNotBlank(userVO.getEmail())) {
				storedUser.setEmail(userVO.getEmail());
			}

			if (userVO.getRoles() != null && !userVO.getRoles().isEmpty() && RoleEnum.containsAll(userVO.getRoles())) {
				storedUser.setRoles(userVO.getRoles());
			}

			if (userVO.getClassificacao() != null) {
				storedUser.setClassificacao(userVO.getClassificacao());
			}
		}

		if (StringUtils.isNotBlank(userVO.getNome())) {
			storedUser.setNome(userVO.getNome());
		}

		if (StringUtils.isNotBlank(userVO.getNovaSenha())) {
			if (AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
				this.updateUserPassword(userVO.getId(), null, userVO.getNovaSenha());
			} else {
				if (StringUtils.isBlank(userVO.getSenha())) {
					throw new IllegalArgumentException("A senha atual não foi informada.");
				}
				this.updateUserPassword(userVO.getId(), userVO.getSenha(), userVO.getNovaSenha());
			}
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

		return ParserUtil.parse(storedUser, UserDTO.class);
	}

	@Override
	@Transactional
	public void updateStatus(Long id, UserStatusEnum status) throws BusinessException {
		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString())) {
			throw new InvalidPermissionException();
		}

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

	// TODO função para atualizar a classificação do usuário (calcular a média e atualizad na base..)

	@Override
	public void recoveryPassword(User usuario) throws BusinessException {
		User user = null;

		if (usuario == null || StringUtils.isBlank(usuario.getEmail())) {
			throw new BusinessException("E-mail informado para a recuperação de senha é inválido.");
		}

		logger.info("Recuperando senha do usuário: " + usuario.getId());

		try {
			user = getDAO().findByEmail(usuario.getEmail(), null);
		} catch (Exception e) {
			throw new BusinessException("E-mail informado para a recuperação de senha é inválido.");
		}

		ITokenService tokenBO = ApplicationBeanFactory.getBean(ITokenService.class);
		Token token = tokenBO.createTokenForPasswordRecovery(user);

		// TODO add MailSender
		// IMailSender mailSender = MailSenderFactory.createEmailSender();
		// mailSender.sendRecoveryPasswordMail(token);

		logger.info("E-mail de recuperação de senha foi enviado com sucesso!");
	}

	@Override
	public void updateForgottenPassword(String tokenHash, String newPassword, Long userId) throws BusinessException {
		Token token = tokenService.validatePasswordForgotToken(tokenHash, userId);
		this.updateUserPassword(token.getUsuario().getId(), null, newPassword);
	}

	@Override
	public UserDTO login(LoginDTO loginDTO) {
		logger.info("Usuário logando: " + loginDTO.getEmail());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha());

		Authentication auth = this.authenticationManager.authenticate(token);
		AuthenticationUtils.login(auth);

		UserDTO dto = null;
		if (auth != null && auth.getPrincipal() instanceof UserCredentials) {
			UserCredentials userDetails = (UserCredentials) auth.getPrincipal();

			User user = super.findByPrimaryKey(userDetails.getId());
			if (user == null) {
				throw new EntityNotFoundException();
			}

			this.userLoginService.saveLastAccess(user);
			dto = ParserUtil.parse(user, UserDTO.class);
		}

		return dto;
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
		super.save(user);

		// TODO add MailSender
		// IMailSender mailSender = MailSenderFactory.createEmailSender();
		// mailSender.sendChangePasswordMail(user.getEmail());
		// logger.info("Enviando email de alteração de senha!");

		logger.info("Senha alterada com sucesso!");
	}

}
