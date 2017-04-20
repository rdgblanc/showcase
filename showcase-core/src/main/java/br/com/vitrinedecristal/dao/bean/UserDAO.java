package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import br.com.vitrinedecristal.dao.IUserDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.enums.UserStatusEnum;
import br.com.vitrinedecristal.model.User;

public class UserDAO extends BaseDAO<Long, User> implements IUserDAO {

	public UserDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	public User findByEmail(String email, List<UserStatusEnum> status) {
		if (StringUtils.isBlank(email)) {
			throw new IllegalArgumentException("O e-mail deve ser informado para a busca de usuário por email.");
		}

		StringBuffer query = new StringBuffer("SELECT u FROM User u WHERE LOWER(u.email) = :email");

		if (status != null) {
			query.append(" AND u.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("email", email.toLowerCase());

		if (status != null) {
			q.setParameter("status", status);
		}

		return (User) q.getSingleResult();
	}

}