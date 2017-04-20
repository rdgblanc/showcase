package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.enums.UserStatusEnum;
import br.com.vitrinedecristal.model.User;

/**
 * Define os contratos de acesso a dados da entidade {@link User}.
 */
public interface IUserDAO extends IBaseDAO<Long, User> {

	User findByEmail(String email, List<UserStatusEnum> status);

}