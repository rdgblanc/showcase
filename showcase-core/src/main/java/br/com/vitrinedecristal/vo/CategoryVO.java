package br.com.vitrinedecristal.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.vitrinedecristal.model.Category;
import br.com.vitrinedecristal.vo.base.BaseVO;

/**
 * Classe VO para representação de um objeto {@link Category}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryVO extends BaseVO<Category> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private CategoryVO categoriaPai;

	public CategoryVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link Category}
	 * 
	 * @param category
	 */
	public CategoryVO(Category category) {
		super(category);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public CategoryVO getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(CategoryVO categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

}