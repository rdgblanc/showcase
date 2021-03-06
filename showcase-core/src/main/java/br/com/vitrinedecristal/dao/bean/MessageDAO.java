package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.IMessageDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.enums.MessageStatusEnum;
import br.com.vitrinedecristal.model.Message;

public class MessageDAO extends BaseDAO<Long, Message> implements IMessageDAO {

	public MessageDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Message> findByUser(Long userId, List<MessageStatusEnum> status) {
		if (userId == null) {
			throw new IllegalArgumentException("O id do usuário deve ser informado para a busca de mensagens por usuário.");
		}

		StringBuffer query = new StringBuffer("SELECT m FROM Message m WHERE m.usuario.id = :userId");

		if (status != null) {
			query.append(" AND m.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("userId", userId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Message>) q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Message> findByNegotiation(Long negotiationId, List<MessageStatusEnum> status) {
		if (negotiationId == null) {
			throw new IllegalArgumentException("O id da negociação deve ser informado para a busca de mensagens por negociação.");
		}

		StringBuffer query = new StringBuffer("SELECT m FROM Message m WHERE m.negociacao.id = :negotiationId");

		if (status != null) {
			query.append(" AND m.status IN (:status)");
		}

		query.append(" ORDER BY m.dtAtualizacao ASC");

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("negotiationId", negotiationId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Message>) q.getResultList();
	}

}