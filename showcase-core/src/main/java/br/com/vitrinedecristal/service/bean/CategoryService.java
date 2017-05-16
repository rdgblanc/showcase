package br.com.vitrinedecristal.service.bean;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import br.com.vitrinedecristal.dao.ICategoryDAO;
import br.com.vitrinedecristal.exception.BusinessException;
import br.com.vitrinedecristal.exception.EntityNotFoundException;
import br.com.vitrinedecristal.model.Category;
import br.com.vitrinedecristal.service.ICategoryService;
import br.com.vitrinedecristal.service.base.BaseService;
import br.com.vitrinedecristal.util.ParserUtil;
import br.com.vitrinedecristal.vo.CategoryVO;

/**
 * Servico para realização de lógicas no negócio para a entidade {@link Category}
 */
public class CategoryService extends BaseService<Long, Category, ICategoryDAO> implements ICategoryService {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(CategoryService.class);

	public CategoryService(ICategoryDAO categoryDAO) {
		super(categoryDAO);
	}

	@Override
	public CategoryVO getCategory(Long id) throws BusinessException {
		logger.info("Obtendo categoria pelo id: " + id);

		Category category = super.findByPrimaryKey(id);
		if (category == null) {
			throw new EntityNotFoundException("Não foi encontrado categoria com o id informado.");
		}

		return ParserUtil.parse(category, CategoryVO.class);
	}

	@Override
	@Transactional
	public CategoryVO createCategory(CategoryVO categoryVO) throws BusinessException {
		if (categoryVO == null) {
			throw new IllegalArgumentException("A entidade categoria não pode ser nula.");
		}

		if (StringUtils.isBlank(categoryVO.getNome())) {
			throw new BusinessException("O campo nome é obrigatório");
		}

		logger.info("Criando categoria: " + categoryVO);
		Category category = ParserUtil.parse(categoryVO, Category.class);
		Category storedCategory = super.save(category);
		logger.info("Endereço criado com sucesso!");

		return ParserUtil.parse(storedCategory, CategoryVO.class);
	}

	@Override
	@Transactional
	public CategoryVO updateCategory(CategoryVO categoryVO) throws BusinessException {
		logger.info("Atualizando categoria: " + categoryVO);

		if (categoryVO == null) {
			throw new IllegalArgumentException("A entidade categoria não pode ser nula.");
		}

		if (categoryVO.getNome() == null) {
			throw new IllegalArgumentException("O nome da categoria não pode ser nulo.");
		}

		Category storedCategory = getDAO().findByPrimaryKey(categoryVO.getId());
		if (storedCategory == null) {
			throw new EntityNotFoundException("Não foi encontrado nenhuma categoria com o id informado.");
		}

		if (StringUtils.isNotBlank(categoryVO.getNome())) {
			storedCategory.setNome(categoryVO.getNome());
		}

		storedCategory = super.save(storedCategory);
		logger.info("Endereço atualizado com sucesso!");

		return ParserUtil.parse(storedCategory, CategoryVO.class);
	}

	@Override
	@Transactional
	public void removeCategory(Long id) throws BusinessException {
		logger.info("Removendo categoria pelo id: " + id);
		Category category = new Category();
		category.setId(id);
		super.remove(category);
		logger.info("Categoria [" + id + "] removida com sucesso!");
	}

	@Override
	public List<CategoryVO> listCategories() throws BusinessException {
		logger.info("Listando categorias..");
		List<Category> listCategories = getDAO().findCategories();
		logger.info("Categorias listadas com sucesso!");

		return ParserUtil.parse(listCategories, CategoryVO.class);
	}

	@Override
	public List<CategoryVO> listSubCategories(Long parentId) throws BusinessException {
		logger.info("Listando sub-categorias: parentId(" + parentId + ")");
		List<Category> listSubCategories = getDAO().findSubCategories(parentId);
		logger.info("Sub-categorias listadas com sucesso!");

		return ParserUtil.parse(listSubCategories, CategoryVO.class);
	}

}
