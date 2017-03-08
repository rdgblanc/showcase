package br.com.vitrinedecristal.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Server error default response")
public class ApiExceptionResponse {

	@ApiModelProperty(value = "error message", required = true, position = 1)
	private String message;

	protected ApiExceptionResponse() {
	}

	public ApiExceptionResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}