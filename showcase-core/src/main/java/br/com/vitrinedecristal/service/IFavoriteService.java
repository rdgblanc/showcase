package br.com.vitrinedecristal.service;

import java.util.List;

import br.com.vitrinedecristal.dao.IFavoriteDAO;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.model.Favorite;
import br.com.vitrinedecristal.service.base.IBaseService;
import br.com.vitrinedecristal.vo.FavoriteVO;

/**
 * Define os contratos de lógica de negócio da entidade {@link Favorite}.
 */
public interface IFavoriteService extends IBaseService<Long, Favorite, IFavoriteDAO> {

	/**
	 * Obtém informações do favorito
	 * 
	 * @param id id do favorito
	 * @return informações do favorito
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	FavoriteVO getFavorite(Long id) throws BusinessException;

	/**
	 * Cria o favorito
	 * 
	 * @param favoriteVO informações para o cadastro do favorito
	 * @return favorito criado
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	FavoriteVO createFavorite(FavoriteVO favoriteVO) throws BusinessException;

	/**
	 * Remove o favorito
	 * 
	 * @param id id do favorito a ser removido
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	void removeFavorite(Long id) throws BusinessException;

	/**
	 * Lista os favoritos do usuário
	 * 
	 * @param userId id do usuário
	 * @return lista de favoritos do usuário
	 * @throws BusinessException
	 */
	// @Secured(ROLE_USER)
	List<FavoriteVO> listFavoriteByUser(Long userId) throws BusinessException;

}
