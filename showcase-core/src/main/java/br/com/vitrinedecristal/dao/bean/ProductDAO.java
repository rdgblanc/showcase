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

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findByCategory(Long categoryId, List<ProductStatusEnum> status) {
		if (categoryId == null) {
			throw new IllegalArgumentException("O id da categoria deve ser informado para a busca de produtos por categoria.");
		}

		StringBuffer query = new StringBuffer("SELECT p FROM Product p WHERE p.categoria IN (SELECT c FROM Category c WHERE c.categoriaPai.id = :categoryId)");

		if (status != null) {
			query.append(" AND p.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString()).setMaxResults(3);
		q.setParameter("categoryId", categoryId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Product>) q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findByCategoryAnotherUser(Long categoryId, Long userId, List<ProductStatusEnum> status) {
		if (categoryId == null) {
			throw new IllegalArgumentException("O id da categoria deve ser informado para a busca de produtos por categoria.");
		}

		if (userId == null) {
			throw new IllegalArgumentException("O id do usuário deve ser informado para a busca de produtos por categoria.");
		}

		StringBuffer query = new StringBuffer("SELECT p FROM Product p WHERE p.usuario.id != :userId AND p.categoria IN (SELECT c FROM Category c WHERE c.categoriaPai.id = :categoryId)");

		if (status != null) {
			query.append(" AND p.status IN (:status)");
		}

		Query q = getEntityManager().createQuery(query.toString()).setMaxResults(3);
		q.setParameter("categoryId", categoryId);
		q.setParameter("userId", userId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Product>) q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findNewProducts(List<ProductStatusEnum> status) {
		StringBuffer query = new StringBuffer("SELECT p FROM Product p");

		if (status != null) {
			query.append(" WHERE p.status IN (:status)");
		}

		query.append(" ORDER BY p.dtAtualizacao DESC");

		Query q = getEntityManager().createQuery(query.toString()).setMaxResults(4);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Product>) q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Product> findNewProductsAnotherUser(Long userId, List<ProductStatusEnum> status) {
		StringBuffer query = new StringBuffer("SELECT p FROM Product p WHERE p.usuario.id != :userId");

		if (status != null) {
			query.append(" AND p.status IN (:status)");
		}

		query.append(" ORDER BY p.dtAtualizacao DESC");

		Query q = getEntityManager().createQuery(query.toString()).setMaxResults(4);
		q.setParameter("userId", userId);

		if (status != null) {
			q.setParameter("status", status);
		}

		return (List<Product>) q.getResultList();
	}

}