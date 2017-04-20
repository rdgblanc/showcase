package br.com.vitrinedecristal.dao.bean;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.ITokenDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.model.Token;

public class TokenDAO extends BaseDAO<Long, Token> implements ITokenDAO {

	public TokenDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	public Token findByHash(String tokenHash) {
		Query q = getEntityManager().createQuery("SELECT t FROM Token t WHERE t.hash = :hash");
		q.setParameter("hash", tokenHash);

		return (Token) q.getSingleResult();
	}

}