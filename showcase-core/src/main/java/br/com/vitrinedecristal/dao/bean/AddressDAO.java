package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.IAddressDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.enums.AddressStatusEnum;
import br.com.vitrinedecristal.model.Address;

public class AddressDAO extends BaseDAO<Long, Address> implements IAddressDAO {

	public AddressDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Address> findByUser(Long userId, List<AddressStatusEnum> status) {
		if (userId == null) {
			throw new IllegalArgumentException("O id do usuário deve ser informado para a busca de endereço por usuário.");
		}

		StringBuffer query = new StringBuffer("SELECT e FROM Address e WHERE e.usuario.id = :userId");

		if (status != null) {
			query.append(" AND e.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("userId", userId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Address>) q.getResultList();
	}

}