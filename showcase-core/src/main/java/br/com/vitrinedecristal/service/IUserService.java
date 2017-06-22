package br.com.vitrinedecristal.service;

import java.util.List;

import org.springframework.security.access.annotation.Secured;

import br.com.vitrinedecristal.dao.IUserDAO;
import br.com.vitrinedecristal.dto.CreateUserDTO;
import br.com.vitrinedecristal.dto.LoginDTO;
import br.com.vitrinedecristal.dto.UpdateUserPasswordDTO;
import br.com.vitrinedecristal.dto.UserDTO;
import br.com.vitrinedecristal.enums.UserStatusEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.UserVO;
import javassist.NotFoundException;

/**
 * Define os contratos de lógica de negócio da entidade {@link User}.
 */
public interface IUserService extends IBaseService<Long, User, IUserDAO> {

	/**
	 * Lista os usuários da base
	 * 
	 * @return lista de usuários
	 * @throws BusinessException
	 */
	@Secured(ROLE_ADMIN)
	List<UserDTO> listUsers() throws BusinessException;

	/**
	 * Obtém informações do usuário
	 * 
	 * @param id id do usuário
	 * @return informações do usuário
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	UserVO getUser(String id) throws BusinessException;

	/**
	 * Cria o usuário
	 * 
	 * @param createUserDTO informações para o cadastro do usuário
	 * @return usuário criado
	 * @throws BusinessException
	 */
	UserDTO createUser(CreateUserDTO createUserDTO) throws BusinessException;

	/**
	 * Atualiza as informações do usuário
	 * 
	 * @param userVO
	 * @return usuário alterado
	 * @throws BusinessException
	 */
	@Secured(ROLE_USER)
	UserVO updateUser(UserVO userVO) throws BusinessException;

	/**
	 * Remove o usuário
	 * 
	 * @param id id do usuário a ser removido
	 * @throws BusinessException
	 */
	@Secured(ROLE_ADMIN)
	void removeUser(Long id) throws BusinessException;

	/**
	 * Altera o status do usuário
	 * 
	 * @param id id do usuário
	 * @param status novo status do usuário
	 * @throws BusinessException
	 */
	void updateStatus(Long id, UserStatusEnum status) throws BusinessException;

	/**
	 * Altera a senha do usuário
	 * 
	 * @param updateUserPasswordDTO objeto com o ID e a nova senha do usuário
	 */
	@Secured(ROLE_USER)
	void updatePassword(UpdateUserPasswordDTO updateUserPasswordDTO) throws BusinessException;

	/**
	 * Envia um e-mail de recuperação de senha para o usuário
	 * 
	 * @param email e-mail do usuário para recuperação de senha
	 * @throws BusinessException
	 */
	void recoveryPassword(String email) throws BusinessException;

	/**
	 * Atualiza password esquecido caso token seja válido.
	 * 
	 * @param token
	 * @param newPassword
	 * @param userId
	 * @throws BusinessException
	 * @throws NotFoundException
	 */
	void updateForgottenPassword(String token, String newPassword, Long userId) throws BusinessException;

	/**
	 * Efetua o login do usuário
	 * 
	 * @param loginDTO informações para o login do usuário
	 * @return usuário logado
	 */
	UserVO login(LoginDTO loginDTO);

}
