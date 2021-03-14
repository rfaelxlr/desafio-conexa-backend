package br.com.desafio_conexa_backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.desafio_conexa_backend.domain.Paciente;
import br.com.desafio_conexa_backend.domain.dto.PacienteDTO;
import br.com.desafio_conexa_backend.repository.PacienteRepository;


@Service
public class PacienteService {
	@Autowired
    private  PacienteRepository pacienteRepository;

   

    public List<PacienteDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(paciente -> mapToDTO(paciente, new PacienteDTO()))
                .collect(Collectors.toList());
    }

    public PacienteDTO get(final Long id) {
        return pacienteRepository.findById(id)
                .map(paciente -> mapToDTO(paciente, new PacienteDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final PacienteDTO pacienteDTO) {
        final Paciente paciente = new Paciente();
        mapToEntity(pacienteDTO, paciente);
        return pacienteRepository.save(paciente).getId();
    }

    public Paciente update(final Long id, final PacienteDTO pacienteDTO) {
        final Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(pacienteDTO, paciente);
        pacienteRepository.save(paciente);
        return paciente;
    }

    public void delete(final Long id) {
        pacienteRepository.deleteById(id);
    }

    private PacienteDTO mapToDTO(final Paciente paciente, final PacienteDTO pacienteDTO) {
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setNome(paciente.getNome());
        pacienteDTO.setCpf(paciente.getCpf());
        pacienteDTO.setIdade(paciente.getIdade());
        pacienteDTO.setTelefone(paciente.getTelefone());
        return pacienteDTO;
    }

    private Paciente mapToEntity(final PacienteDTO pacienteDTO, final Paciente paciente) {
        paciente.setNome(pacienteDTO.getNome());
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setIdade(pacienteDTO.getIdade());
        paciente.setTelefone(pacienteDTO.getTelefone());
        return paciente;
    }

}
