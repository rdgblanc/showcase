package br.com.vitrinedecristal.service;

import br.com.vitrinedecristal.dao.IUserLoginDAO;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.model.UserLogin;
import br.com.vitrinedecristal.service.base.IBaseService;

/**
 * Define os contratos de lógica de negócio da entidade {@link UserLogin}.
 */
public interface IUserLoginService extends IBaseService<Long, UserLogin, IUserLoginDAO> {

	/**
	 * Insere o registro de último acesso (autenticação) do usuário
	 * 
	 * @param user Entidade {@link User}
	 */
	void saveLastAccess(User user);
}
