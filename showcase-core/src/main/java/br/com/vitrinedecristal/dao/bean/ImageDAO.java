package br.com.vitrinedecristal.dao.bean;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.vitrinedecristal.dao.IImageDAO;
import br.com.vitrinedecristal.dao.base.BaseDAO;
import br.com.vitrinedecristal.model.Image;

public class ImageDAO extends BaseDAO<Long, Image> implements IImageDAO {

	public ImageDAO(EntityManager manager) {
		super(manager);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Image> findByProduct(Long productId) {
		if (productId == null) {
			throw new IllegalArgumentException("O id do produto deve ser informado para a busca de imagens por produto.");
		}

		StringBuffer query = new StringBuffer("SELECT p FROM Product p WHERE p.produto.id = :productId");

		Query q = getEntityManager().createQuery(query.toString());
		q.setParameter("productId", productId);

		return (List<Image>) q.getResultList();
	}

}