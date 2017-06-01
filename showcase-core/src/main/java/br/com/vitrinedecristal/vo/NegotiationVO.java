package br.com.vitrinedecristal.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.vitrinedecristal.enums.NegotiationNegotiationTypeEnum;
import br.com.vitrinedecristal.enums.NegotiationStatusEnum;
import br.com.vitrinedecristal.model.Negotiation;
import br.com.vitrinedecristal.vo.base.BaseVO;

/**
 * Classe VO para representação de um objeto {@link Negotiation}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NegotiationVO extends BaseVO<Negotiation> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private NegotiationNegotiationTypeEnum tipoNegociacao;

	private String observacao;

	private Float valorPgto;

	private Date dtTransacao;

	private Date dtAtualizacao;

	private NegotiationStatusEnum status;

	private ProductVO produto;

	private UserVO usuario;

	public NegotiationVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link Negotiation}
	 * 
	 * @param negotiation
	 */
	public NegotiationVO(Negotiation negotiation) {
		super(negotiation);
	}

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