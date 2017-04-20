package br.com.vitrinedecristal.dao;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.model.UserLogin;

/**
 * Define os contratos de acesso a dados da entidade {@link UserLogin}.
 */
public interface IUserLoginDAO extends IBaseDAO<Long, UserLogin> {
}