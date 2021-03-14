package br.com.desafio_conexa_backend.domain.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialsDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String usuario;
	@NotNull
	private String senha;

}
