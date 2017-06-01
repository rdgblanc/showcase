package br.com.vitrinedecristal.dao;

import java.util.List;

import br.com.vitrinedecristal.dao.base.IBaseDAO;
import br.com.vitrinedecristal.model.Favorite;

/**
 * Define os contratos de acesso a dados da entidade {@link Favorite}.
 */
public interface IFavoriteDAO extends IBaseDAO<Long, Favorite> {

	List<Favorite> findByUser(Long userId);

}