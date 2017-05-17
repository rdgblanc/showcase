package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.IProductDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.enums.ProductStatusEnum;
import br.com.vitrinedecristal.model.Product;

public class ProductDAO extends BaseDAO<Long, Product> implements IProductDAO {

	public ProductDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findByUser(Long userId, List<ProductStatusEnum> status) {
		if (userId == null) {
			throw new IllegalArgumentException("O id do usuário deve ser informado para a busca de produtos por usuário.");
		}

		StringBuffer query = new StringBuffer("SELECT p FROM Product p WHERE p.usuario.id = :userId");

		if (status != null) {
			query.append(" AND p.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("userId", userId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Product>) q.getResultList();
	}

}