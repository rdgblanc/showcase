package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.ICategoryDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.model.Category;

public class CategoryDAO extends BaseDAO<Long, Category> implements ICategoryDAO {

	public CategoryDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> findCategories() {
		StringBuffer query = new StringBuffer("SELECT c FROM Category c WHERE c.categoriaPai.id IS NULL ORDER BY c.nome");

		Query q = getEntityManager().createQuery(query.toString());
		return (List<Category>) q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Category> findSubCategories(Long parentId) {
		StringBuffer query = new StringBuffer("SELECT c FROM Category c WHERE c.categoriaPai.id = :parentId ORDER BY c.nome");

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("parentId", parentId);

		return (List<Category>) q.getResultList();
	}

}