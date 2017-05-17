package br.com.vitrinedecristal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.vitrinedecristal.dao.base.IID;
import br.com.vitrinedecristal.enums.ProductConservationStateEnum;
import br.com.vitrinedecristal.enums.ProductNegotiationTypeEnum;
import br.com.vitrinedecristal.enums.ProductStatusEnum;

@Entity
@Table(name = "TB_PRODUTO")
public class Product implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NOME", length = 45, nullable = false)
	private String nome;

	@Column(name = "DESCRICAO", length = 1000, nullable = false)
	private String descricao;

	@Column(name = "TAMANHO", length = 45, nullable = true)
	private String tamanho;

	@Column(name = "MODELO", length = 100, nullable = true)
	private String modelo;

	@Column(name = "MARCA", length = 100, nullable = true)
	private String marca;

	@Column(name = "PRECO", nullable = true)
	private Float preco;

	@Column(name = "QUANTIDADE", nullable = false)
	private Integer quantidade;

	@Column(name = "ESTADO_CONSERVACAO", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ProductConservationStateEnum estadoConservacao;

	@Column(name = "TIPO_NEGOCIACAO", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ProductNegotiationTypeEnum tipoNegociacao;

	@Column(name = "CLASSIFICACAO", nullable = true)
	private Float classificacao;

	@Column(name = "DT_ATUALIZACAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAtualizacao;

	@Column(name = "STATUS", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ProductStatusEnum status;

	@ManyToOne(targetEntity = Category.class)
	@JoinColumn(name = "ID_CATEGORIA", nullable = false)
	private Category categoria;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private User usuario;

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

	public Category getCategoria() {
		return categoria;
	}

	public void setCategoria(Category categoria) {
		this.categoria = categoria;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

}