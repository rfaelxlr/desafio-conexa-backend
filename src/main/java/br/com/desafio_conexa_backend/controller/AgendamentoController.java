package br.com.desafio_conexa_backend.controller;

import br.com.desafio_conexa_backend.domain.dto.AgendamentoDTO;
import br.com.desafio_conexa_backend.service.AgendamentoService;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/agendamentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class AgendamentoController {
	@Autowired
	private AgendamentoService agendamentoService;

	@GetMapping
	public ResponseEntity<List<AgendamentoDTO>> getAllAgendamentos() {
		return ResponseEntity.ok(agendamentoService.findAll());
	}

	@PostMapping
	public ResponseEntity<Long> createAgendamento(@RequestBody @Valid final AgendamentoDTO agendamentoDTO) {
		return new ResponseEntity<>(agendamentoService.create(agendamentoDTO), HttpStatus.CREATED);
	}

}
