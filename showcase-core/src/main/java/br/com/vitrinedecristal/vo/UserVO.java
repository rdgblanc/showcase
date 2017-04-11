package br.com.vitrinedecristal.vo;

import java.util.Date;

import br.com.vitrinedecristal.annotation.IgnoreVOParser;
import br.com.vitrinedecristal.enums.GenderEnum;
import br.com.vitrinedecristal.enums.UserStatusEnum;
import br.com.vitrinedecristal.model.User;
import br.com.vitrinedecristal.security.credential.Roles;

/**
 * Classe VO para representação de um objeto {@link User} nos clientes.
 */
public class UserVO extends BaseVO<User> {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;

	private String email;

	private String senha;

	@IgnoreVOParser
	private String novaSenha;

	private GenderEnum sexo;

	private String telefone;

	private Date dtNascimento;

	private Float classificacao;

	private Date dtAtualizacao;

	private UserStatusEnum status;

	private Roles role;

	public UserVO() {
	}

	/**
	 * Construtor para a criação via um objeto {@link User}
	 * 
	 * @param user
	 */
	public UserVO(User user) {
		super(user);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public GenderEnum getSexo() {
		return sexo;
	}

	public void setSexo(GenderEnum sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
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

	public UserStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UserStatusEnum status) {
		this.status = status;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

}
