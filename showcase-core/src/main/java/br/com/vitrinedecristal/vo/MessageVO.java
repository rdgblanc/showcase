package br.com.vitrinedecristal.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import br.com.vitrinedecristal.enums.MessageStatusEnum;
import br.com.vitrinedecristal.model.Message;
import br.com.vitrinedecristal.vo.base.BaseVO;

/**
 * Classe VO para representação de um objeto {@link Message}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageVO extends BaseVO<Message> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String texto;

	private Date dtAtualizacao;

	private MessageStatusEnum status;

	private NegotiationVO negociacao;

	private UserVO usuario;

	public MessageVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link Message}
	 * 
	 * @param message
	 */
	public MessageVO(Message message) {
		super(message);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public MessageStatusEnum getStatus() {
		return status;
	}

	public void setStatus(MessageStatusEnum status) {
		this.status = status;
	}

	public NegotiationVO getNegociacao() {
		return negociacao;
	}

	public void setNegociacao(NegotiationVO negociacao) {
		this.negociacao = negociacao;
	}

	public UserVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserVO usuario) {
		this.usuario = usuario;
	}

}