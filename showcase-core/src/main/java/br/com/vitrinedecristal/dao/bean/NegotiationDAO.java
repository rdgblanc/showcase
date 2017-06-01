package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.INegotiationDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.enums.NegotiationStatusEnum;
import br.com.vitrinedecristal.model.Negotiation;

public class NegotiationDAO extends BaseDAO<Long, Negotiation> implements INegotiationDAO {

	public NegotiationDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Negotiation> findByUser(Long userId, List<NegotiationStatusEnum> status) {
		if (userId == null) {
			throw new IllegalArgumentException("O id do usuário deve ser informado para a busca de negociações por usuário.");
		}

		StringBuffer query = new StringBuffer("SELECT n FROM Negotiation n WHERE n.usuario.id = :userId");

		if (status != null) {
			query.append(" AND n.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("userId", userId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Negotiation>) q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Negotiation> findByProduct(Long productId, List<NegotiationStatusEnum> status) {
		if (productId == null) {
			throw new IllegalArgumentException("O id do produto deve ser informado para a busca de negociações por produto.");
		}

		StringBuffer query = new StringBuffer("SELECT n FROM Negotiation n WHERE n.produto.id = :productId");

		if (status != null) {
			query.append(" AND n.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("productId", productId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Negotiation>) q.getResultList();
	}

}