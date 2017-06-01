package br.com.vitrinedecristal.service.bean;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;

import br.com.vitrinedecristal.dao.IFavoriteDAO;
import br.com.vitrinedecristal.enums.RoleEnum;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.exception.InvalidPermissionException;
import br.com.vitrinedecristal.model.Favorite;
import br.com.vitrinedecristal.security.util.AuthenticationUtils;
import br.com.vitrinedecristal.service.IFavoriteService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.FavoriteVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link Favorite}
 */
public class FavoriteService extends BaseService<Long, Favorite, IFavoriteDAO> implements IFavoriteService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(FavoriteService.class);

	public FavoriteService(IFavoriteDAO favoriteDAO) {
		super(favoriteDAO);
	}

	@Override
	public FavoriteVO getFavorite(Long id) throws BusinessException {
		logger.info("Obtendo favorito pelo id: " + id);

		Favorite favorite = super.findByPrimaryKey(id);
		if (favorite == null) {
			throw new EntityNotFoundException("Não foi encontrado favorito com o id informado.");
		}

		return ParserUtil.parse(favorite, FavoriteVO.class);
	}

	@Override
	@Transactional
	public FavoriteVO createFavorite(FavoriteVO favoriteVO) throws BusinessException {
		logger.info("Criando favorito: " + favoriteVO);

		if (favoriteVO == null) {
			throw new IllegalArgumentException("A entidade favorito não pode ser nula.");
		}

		if (favoriteVO.getUsuario() == null || favoriteVO.getUsuario().getId() == null) {
			throw new IllegalArgumentException("O usuário do favorito não pode ser nulo ou conter id nulo.");
		}

		if (favoriteVO.getProduto() == null || favoriteVO.getProduto().getId() == null) {
			throw new IllegalArgumentException("O produto do favorito não pode ser nulo ou conter id nulo.");
		}

		if (!AuthenticationUtils.listUserRoles().contains(RoleEnum.ROLE_ADMIN.toString()) && !favoriteVO.getUsuario().getId().equals(AuthenticationUtils.getUserId())) {
			throw new InvalidPermissionException();
		}

		Favorite favorite = ParserUtil.parse(favoriteVO, Favorite.class);
		favorite.setDtInsercao(new Date());

		Favorite storedFavorite = super.save(favorite);
		logger.info("Favorito criado com sucesso!");

		return ParserUtil.parse(storedFavorite, FavoriteVO.class);
	}

	@Override
	@Transactional
	public void removeFavorite(Long id) throws BusinessException {
		logger.info("Removendo favorito pelo id: " + id);
		Favorite favorite = new Favorite();
		favorite.setId(id);
		super.remove(favorite);
		logger.info("Favorito [" + id + "] removido com sucesso!");
	}

	@Override
	public List<FavoriteVO> listFavoriteByUser(Long userId) throws BusinessException {
		logger.info("Listando os favoritos do usuário: " + userId);
		List<Favorite> listProduct = getDAO().findByUser(userId);
		logger.info("Favoritos listados com sucesso!");

		return ParserUtil.parse(listProduct, FavoriteVO.class);
	}

}
