package br.com.vitrinedecristal.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class LoginDTO implements Serializable {

	private String email;

	private String senha;

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

}