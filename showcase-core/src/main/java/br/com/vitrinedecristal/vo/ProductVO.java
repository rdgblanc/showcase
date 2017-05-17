package br.com.vitrinedecristal.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.vitrinedecristal.enums.ProductConservationStateEnum;
import br.com.vitrinedecristal.enums.ProductNegotiationTypeEnum;
import br.com.vitrinedecristal.enums.ProductStatusEnum;
import br.com.vitrinedecristal.model.Product;
import br.com.vitrinedecristal.vo.base.BaseVO;

/**
 * Classe VO para representação de um objeto {@link Product}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductVO extends BaseVO<Product> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private String descricao;

	private String tamanho;

	private String modelo;

	private String marca;

	private Float preco;

	private Integer quantidade;

	private ProductConservationStateEnum estadoConservacao;

	private ProductNegotiationTypeEnum tipoNegociacao;

	private Float classificacao;

	private Date dtAtualizacao;

	private ProductStatusEnum status;

	private CategoryVO categoria;

	private UserVO usuario;

	public ProductVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link Product}
	 * 
	 * @param address
	 */
	public ProductVO(Product product) {
		super(product);
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public ProductConservationStateEnum getEstadoConservacao() {
		return estadoConservacao;
	}

	public void setEstadoConservacao(ProductConservationStateEnum estadoConservacao) {
		this.estadoConservacao = estadoConservacao;
	}

	public ProductNegotiationTypeEnum getTipoNegociacao() {
		return tipoNegociacao;
	}

	public void setTipoNegociacao(ProductNegotiationTypeEnum tipoNegociacao) {
		this.tipoNegociacao = tipoNegociacao;
	}

	public Float getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Float classificacao) {
		this.classificacao = classificacao;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public ProductStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ProductStatusEnum status) {
		this.status = status;
	}

	public CategoryVO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoryVO categoria) {
		this.categoria = categoria;
	}

	public UserVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserVO usuario) {
		this.usuario = usuario;
	}

}