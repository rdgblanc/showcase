package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.model.Image;

/**
 * Define os contratos de acesso a dados da entidade {@link Image}.
 */
public interface IImageDAO extends IBaseDAO<Long, Image> {

	List<Image> findByProduct(Long productId);

}