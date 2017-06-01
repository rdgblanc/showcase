package br.com.vitrinedecristal.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ImageDTO implements Serializable {

	private Long id;

	private String caminho;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

}