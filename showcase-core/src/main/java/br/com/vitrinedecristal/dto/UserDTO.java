package br.com.vitrinedecristal.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserDTO implements Serializable {

	private String id;

	private String nome;

	private String email;

	public String getUid() {
		return id;
	}

	public void setUid(String uid) {
		this.id = uid;
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

}