package br.com.desafio_conexa_backend.controller;

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

import br.com.desafio_conexa_backend.domain.Paciente;
import br.com.desafio_conexa_backend.domain.dto.PacienteDTO;
import br.com.desafio_conexa_backend.service.PacienteService;

@RestController
@RequestMapping(value = "/api/pacientes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PacienteController {
	@Autowired
	private PacienteService pacienteService;

	@GetMapping
	public ResponseEntity<List<PacienteDTO>> getAllPacientes() {
		return ResponseEntity.ok(pacienteService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PacienteDTO> getPaciente(@PathVariable final Long id) {
		return ResponseEntity.ok(pacienteService.get(id));
	}

	@PostMapping
	public ResponseEntity<Long> createPaciente(@RequestBody @Valid final PacienteDTO pacienteDTO) {
		return new ResponseEntity<>(pacienteService.create(pacienteDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Paciente> updatePaciente(@PathVariable final Long id,
			@RequestBody @Valid final PacienteDTO pacienteDTO) {
		Paciente paciente = pacienteService.update(id, pacienteDTO);
		return ResponseEntity.ok(paciente);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePaciente(@PathVariable final Long id) {
		pacienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
