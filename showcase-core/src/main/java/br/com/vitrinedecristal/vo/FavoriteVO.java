package br.com.vitrinedecristal.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.vitrinedecristal.model.Favorite;
import br.com.vitrinedecristal.vo.base.BaseVO;

/**
 * Classe VO para representação de um objeto {@link Favorite}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FavoriteVO extends BaseVO<Favorite> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private Date dtInsercao;

	private ProductVO produto;

	private UserVO usuario;

	public FavoriteVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link Favorite}
	 * 
	 * @param favorite
	 */
	public FavoriteVO(Favorite favorite) {
		super(favorite);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDtInsercao() {
		return dtInsercao;
	}

	public void setDtInsercao(Date dtInsercao) {
		this.dtInsercao = dtInsercao;
	}

	public ProductVO getProduto() {
		return produto;
	}

	public void setProduto(ProductVO produto) {
		this.produto = produto;
	}

	public UserVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserVO usuario) {
		this.usuario = usuario;
	}

}