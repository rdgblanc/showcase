package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.enums.ProductStatusEnum;
import br.com.vitrinedecristal.model.Product;

/**
 * Define os contratos de acesso a dados da entidade {@link Product}.
 */
public interface IProductDAO extends IBaseDAO<Long, Product> {

	List<Product> findByUser(Long userId, List<ProductStatusEnum> status);

	List<Product> findByCategory(Long categoryId, List<ProductStatusEnum> status);

}