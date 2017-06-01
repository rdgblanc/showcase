package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.IFavoriteDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.model.Favorite;

public class FavoriteDAO extends BaseDAO<Long, Favorite> implements IFavoriteDAO {

	public FavoriteDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Favorite> findByUser(Long userId) {
		if (userId == null) {
			throw new IllegalArgumentException("O id do usuário deve ser informado para a busca de favoritos por usuário.");
		}

		StringBuffer query = new StringBuffer("SELECT f FROM Favorite f WHERE f.usuario.id = :userId");

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("userId", userId);

		return (List<Favorite>) q.getResultList();
	}

}