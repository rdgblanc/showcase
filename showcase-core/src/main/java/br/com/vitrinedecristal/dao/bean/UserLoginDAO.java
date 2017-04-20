package br.com.vitrinedecristal.dao.bean;

import javax.persistence.EntityManager;

import br.com.vitrinedecristal.dao.IUserLoginDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.model.UserLogin;

public class UserLoginDAO extends BaseDAO<Long, UserLogin> implements IUserLoginDAO {

	public UserLoginDAO(EntityManager manager) {
		super(manager);
	}

}