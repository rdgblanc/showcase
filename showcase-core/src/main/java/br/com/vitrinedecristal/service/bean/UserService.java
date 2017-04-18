package br.com.vitrinedecristal.service.bean;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.vitrinedecristal.application.ApplicationBeanFactory;
import br.com.vitrinedecristal.dao.IUserDAO;
import br.com.vitrinedecristal.dto.LoginDTO;
import br.com.vitrinedecristal.dto.UserDTO;
import br.com.vitrinedecristal.enums.UserStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EncryptPasswordException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.exception.UserAlreadyExistsException;
import br.com.vitrinedecristal.log.TrackingLogger;
import br.com.vitrinedecristal.model.Token;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.security.credential.Roles;
import br.com.vitrinedecristal.security.credential.UserCredentials;
import br.com.vitrinedecristal.security.util.AuthenticationUtils;
import br.com.vitrinedecristal.service.ITokenService;
import br.com.vitrinedecristal.service.IUserService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.UserVO;
import javassist.NotFoundException;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link User}
 */
public class UserService extends BaseService<Long, User, IUserDAO> implements IUserService {

	private static final long serialVersionUID = 1L;

	/**
	 * String para ser usada junto da senha dos usuários. Deve ser mantido em segredo.
	 */
	private static final String PASSWORD_SEED = "vcristal:";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ITokenService tokenService;

	private static final Logger logger = TrackingLogger.getLogger(UserService.class);

	public UserService(IUserDAO userDAO) {
		super(userDAO);
	}

	@Override
	public List<UserDTO> listUsers() throws BusinessException {
		logger.info("Listando os usuários");
		List<User> users = super.find();
		logger.info("Usuários listados com sucesso");

		return ParserUtil.getVO(users, UserDTO.class);
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

		logger.info("Criando usuário: " + userVO);
		userVO.setStatus(UserStatusEnum.ACTIVE);
		userVO.setDtAtualizacao(new Date());
		// userVO.setRole(Roles.ROLE_USER); TODO validar ROLEs dos usuários
		//userVO.setRole(Roles.ROLE_USER);
		User user = super.save(ParserUtil.getVO(userVO, User.class));
		logger.info("Usuário criado com sucesso!");

		return ParserUtil.getVO(user, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserVO userVO) throws NotFoundException, BusinessException {
		if (userVO == null) {
			throw new IllegalArgumentException("A entidade usuário não pode ser nula.");
		}

		logger.info("Atualizando usuário: " + userVO);
		User userToUpdate = null;

		try {
			userToUpdate = getDAO().findByPrimaryKey(userVO.getId());
		} catch (NoResultException e) {
			throw new NotFoundException("Usuário não encontrado", e);
		}

		if (StringUtils.isBlank(userVO.getNome())) {
			userToUpdate.setNome(userVO.getNome());
		}

		// TODO validar essa alteração para usuários Roles.ADMIN
		// if (StringUtils.isBlank(userVO.getEmail())) {
		// this.validateEmail(userVO.getEmail());
		// userToUpdate.setEmail(userVO.getEmail());
		// }

		if (StringUtils.isNotBlank(userVO.getNovaSenha())) {
			// TODO para usuários admin, a alteração de senha não solicita a senha atual
			// this.updateUserPassword(userVO.getId(), null, userVO.getNovaSenha());
			if (StringUtils.isBlank(userVO.getSenha())) {
				throw new IllegalArgumentException("A senha atual não foi informada.");
			}
			this.updateUserPassword(userVO.getId(), userVO.getSenha(), userVO.getNovaSenha());
		}

		if (userVO.getSexo() != null) {
			userToUpdate.setSexo(userVO.getSexo());
		}

		if (StringUtils.isBlank(userVO.getTelefone())) {
			userToUpdate.setEmail(userVO.getTelefone());
		}

		if (userVO.getDtNascimento() != null) {
			userToUpdate.setDtNascimento(userVO.getDtNascimento());
		}

		if (userVO.getClassificacao() != null) {
			userToUpdate.setClassificacao(userVO.getClassificacao());
		}

		// userVO.setRole(Roles.ROLE_USER); TODO validar alteração de ROLEs dos usuários

		userToUpdate.setStatus(UserStatusEnum.ACTIVE);
		userToUpdate.setDtAtualizacao(new Date());
		userToUpdate = getDAO().save(userToUpdate);
		logger.info("Usuário atualizado com sucesso!");

		return ParserUtil.getVO(userToUpdate, UserDTO.class);
	}

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
	public void updateForgottenPassword(String tokenHash, String newPassword, Long userId) throws BusinessException, EncryptPasswordException, NotFoundException {
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

			User user = getDAO().findByPrimaryKey(userDetails.getId());
			if (user == null) {
				throw new EntityNotFoundException();
			}

			this.updateLastAccess(user);
			dto = ParserUtil.getVO(user, UserDTO.class);
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
	 * Criptografa uma senha usando o algoritmo MD5.
	 * 
	 * @param password a senha a ser criptografada.
	 * @return a senha criptografada.
	 * @throws EncryptPasswordException caso não seja possível criptografar a senha.
	 */
	private String encryptPassword(String password) throws EncryptPasswordException {
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("A senha a ser criptografada não pode ser nula.");
		}

		logger.info("Criptografando senha: " + password);

		String sign = PASSWORD_SEED + password;
		StringBuffer hexString = new StringBuffer();
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new EncryptPasswordException(e);
		}

		md.update(sign.getBytes());
		byte[] userProfileId = md.digest();

		for (int i = 0; i < userProfileId.length; i++) {
			if ((0xff & userProfileId[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & userProfileId[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & userProfileId[i]));
			}
		}

		sign = hexString.toString();

		logger.info("Senha criptografada com sucesso!");
		return sign;
	}

	/**
	 * Altera a senha do usuário, caso a senha atual seja informada valida-se se está correta com a da base.
	 * 
	 * @param userId o ID do usuário
	 * @param password a senha atual
	 * @param newPassword a nova senha
	 * @throws BusinessException
	 * @throws EncryptPasswordException
	 * @throws NotFoundException
	 */
	private void updateUserPassword(Long userId, String password, String newPassword) throws BusinessException, EncryptPasswordException, NotFoundException {
		if (StringUtils.isBlank(newPassword)) {
			throw new IllegalArgumentException("A nova senha não foi informada.");
		}

		logger.info("Alterando senha atual do usuário para uma nova senha: [" + newPassword + "]");

		User user;
		try {
			user = getDAO().findByPrimaryKey(userId);
		} catch (NoResultException e) {
			throw new NotFoundException("Usuário não encontrado.", e);
		}

		if (password != null) {
			String encryptedPassword = encryptPassword(password);
			if (!encryptedPassword.equals(user.getSenha())) {
				throw new BusinessException("A senha informada para realizar a troca não é válida com a senha cadastrada na base de dados.");
			}
		}

		String newPasswordEncrypted = encryptPassword(newPassword);
		if (user.getSenha().equals(newPasswordEncrypted)) {
			throw new BusinessException("A nova senha é igual a senha anterior.");
		}

		user.setSenha(newPasswordEncrypted);
		this.save(user);

		// TODO add MailSender
		// IMailSender mailSender = MailSenderFactory.createEmailSender();
		// mailSender.sendChangePasswordMail(user.getEmail());
		// logger.info("Enviando email de alteração de senha!");

		logger.info("Senha alterada com sucesso!");
	}

	/**
	 * Atualiza o registro de último acesso (autenticação) do usuário
	 * 
	 * @param user Entidade {@link User}
	 */
	private void updateLastAccess(User user) {
		logger.info("Atualizando último acesso do usuário: " + user.getId());
		// TODO atualizar a tabela TB_LOGIN_USUARIO
		logger.info("Atualização de acesso realizada com sucesso!");
	}

}
