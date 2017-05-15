package br.com.vitrinedecristal.vo;

import java.util.Date;

import br.com.vitrinedecristal.enums.AddressStatusEnum;
import br.com.vitrinedecristal.model.Address;
import br.com.vitrinedecristal.vo.base.BaseVO;

/**
 * Classe VO para representação de um objeto {@link Address}.
 */
public class AddressVO extends BaseVO<Address> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String descricao;

	private String cep;

	private String endereco;

	private Integer numero;

	private String complemento;

	private String bairro;

	private String cidade;

	private String estado;

	private Date dtAtualizacao;

	private AddressStatusEnum status;

	private UserVO usuario;

	public AddressVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link Address}
	 * 
	 * @param address
	 */
	public AddressVO(Address address) {
		super(address);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getDtAtualizacao() {
		return dtAtualizacao;
	}

	public void setDtAtualizacao(Date dtAtualizacao) {
		this.dtAtualizacao = dtAtualizacao;
	}

	public AddressStatusEnum getStatus() {
		return status;
	}

	public void setStatus(AddressStatusEnum status) {
		this.status = status;
	}

	public UserVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserVO usuario) {
		this.usuario = usuario;
	}

}