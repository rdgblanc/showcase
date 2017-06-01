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
import br.com.vitrinedecristal.enums.NegotiationNegotiationTypeEnum;
import br.com.vitrinedecristal.enums.NegotiationStatusEnum;

@Entity
@Table(name = "TB_NEGOCIACAO")
public class Negotiation implements IID<Long> {

	@Id
	@Column(name = "ID", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TIPO_NEGOCIACAO", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private NegotiationNegotiationTypeEnum tipoNegociacao;

	@Column(name = "OBSERVACAO", length = 500, nullable = true)
	private String observacao;

	@Column(name = "VALOR_PGTO", nullable = true)
	private Float valorPgto;

	@Column(name = "DT_TRANSACAO", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtTransacao;

	@Column(name = "DT_ATUALIZACAO", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtAtualizacao;

	@Column(name = "STATUS", nullable = false)
	@Enumerated(value = EnumType.STRING)
	private NegotiationStatusEnum status;

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "ID_PRODUTO", nullable = false)
	private Product produto;

	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
	private User usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NegotiationNegotiationTypeEnum getTipoNegociacao() {
		return tipoNegociacao;
	}

	public void setTipoNegociacao(NegotiationNegotiationTypeEnum tipoNegociacao) {
		this.tipoNegociacao = tipoNegociacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Float getValorPgto() {
		return valorPgto;
	}

	public void setValorPgto(Float valorPgto) {
		this.valorPgto = valorPgto;
	}

	public Date getDtTransacao() {
		return dtTransacao;
	}

	public void setDtTransacao(Date dtTransacao) {
		this.dtTransacao = dtTransacao;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public NegotiationStatusEnum getStatus() {
		return status;
	}

	public void setStatus(NegotiationStatusEnum status) {
		this.status = status;
	}

	public Product getProduto() {
		return produto;
	}

	public void setProduto(Product produto) {
		this.produto = produto;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

}