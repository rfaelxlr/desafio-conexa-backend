package br.com.desafio_conexa_backend.domain.dto;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PacienteDTO {

    private Long id;

    @Size(max = 255)
    private String nome;

    
    @Size(max = 255)
    private String cpf;

    @Size(max = 255)
    private String idade;

    @Size(max = 255)
    private String telefone;

}
