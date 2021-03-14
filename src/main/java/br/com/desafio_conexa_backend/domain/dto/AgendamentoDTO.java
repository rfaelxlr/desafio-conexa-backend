package br.com.desafio_conexa_backend.domain.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AgendamentoDTO {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String dataHoraAtendimento;


    @NotNull
    private Long paciente;

}
