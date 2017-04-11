package br.com.vitrinedecristal.service;

import java.util.List;

import br.com.vitrinedecristal.dao.IUserDAO;
import br.com.vitrinedecristal.dto.LoginDTO;
import br.com.vitrinedecristal.dto.UserDTO;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EncryptPasswordException;
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
	List<UserDTO> listUsers() throws BusinessException;

	/**
	 * Cria o usuário
	 * 
	 * @param userVO
	 * @return usuário criado
	 * @throws BusinessException
	 */
	UserDTO createUser(UserVO userVO) throws BusinessException;

	/**
	 * Atualiza as informações do usuário
	 * 
	 * @param userVO
	 * @return usuário alterado
	 * @throws NotFoundException
	 * @throws EncryptPasswordException
	 * @throws BusinessException
	 */
	UserDTO updateUser(UserVO userVO) throws NotFoundException, EncryptPasswordException, BusinessException;

	/**
	 * Envia um e-mail de recuperação de senha para o usuário
	 * 
	 * @param email e-mail do usuário para recuperação de senha
	 * @throws BusinessException
	 */
	void recoveryPassword(User email) throws BusinessException;

	/**
	 * Atualiza password esquecido caso token seja válido.
	 * 
	 * @param token
	 * @param newPassword
	 * @param userId
	 * @throws EncryptPasswordException
	 * @throws BusinessException
	 * @throws NotFoundException
	 */
	void updateForgottenPassword(String token, String newPassword, Long userId) throws BusinessException, EncryptPasswordException, NotFoundException;

	/**
	 * Efetua o login do usuário
	 * 
	 * @param loginDTO informações para o login do usuário
	 * @return usuário logado
	 */
	UserDTO login(LoginDTO loginDTO);

}
