package br.com.desafio_conexa_backend.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.desafio_conexa_backend.domain.Agendamento;
import br.com.desafio_conexa_backend.domain.Medico;
import br.com.desafio_conexa_backend.domain.Paciente;
import br.com.desafio_conexa_backend.domain.dto.AgendamentoDTO;
import br.com.desafio_conexa_backend.repository.AgendamentoRepository;
import br.com.desafio_conexa_backend.repository.MedicoRepository;
import br.com.desafio_conexa_backend.repository.PacienteRepository;
import br.com.desafio_conexa_backend.security.UserSS;

@Service
public class AgendamentoService {
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;

	public List<AgendamentoDTO> findAll() {
		UserSS medicoAuth = UserServiceSS.authenticated();
		Medico medico = medicoRepository.findById(medicoAuth.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "medico not found"));
		List<AgendamentoDTO> list = agendamentoRepository.findAllByMedico(medico.getId()).stream().map(agendamento -> mapToDTO(agendamento, new AgendamentoDTO()))
				.collect(Collectors.toList());
		//Filtar para retornar os agendamentos do dia atual
		list = list.stream().filter(ag -> ag.getDataHoraAtendimento().contains(LocalDate.now().toString())).collect(Collectors.toList());
		return list;
	}
	
	public List<AgendamentoDTO> findAllById(Long id) {
		
		List<AgendamentoDTO> list = agendamentoRepository.findAllByMedico(id).stream().map(agendamento -> mapToDTO(agendamento, new AgendamentoDTO()))
				.collect(Collectors.toList());
		//Filtar para retornar os agendamentos do dia atual
		list = list.stream().filter(ag -> ag.getDataHoraAtendimento().contains(LocalDate.now().toString())).collect(Collectors.toList());
		return list;
	}

	public AgendamentoDTO get(final Long id) {
		return agendamentoRepository.findById(id).map(agendamento -> mapToDTO(agendamento, new AgendamentoDTO()))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}

	public Long create(final AgendamentoDTO agendamentoDTO) {
		final Agendamento agendamento = new Agendamento();
		mapToEntity(agendamentoDTO, agendamento);
		return agendamentoRepository.save(agendamento).getId();
	}

	private AgendamentoDTO mapToDTO(final Agendamento agendamento, final AgendamentoDTO agendamentoDTO) {
		agendamentoDTO.setId(agendamento.getId());
		agendamentoDTO.setDataHoraAtendimento(agendamento.getDataHoraAtendimento());
		agendamentoDTO.setPaciente(agendamento.getPaciente() == null ? null : agendamento.getPaciente().getId());
		return agendamentoDTO;
	}

	private Agendamento mapToEntity(final AgendamentoDTO agendamentoDTO, final Agendamento agendamento) {
		UserSS medicoAuth = UserServiceSS.authenticated();
		Medico medico = medicoRepository.findById(medicoAuth.getId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "medico not found"));
		;

		agendamento.setDataHoraAtendimento(agendamentoDTO.getDataHoraAtendimento());
		if (medico != null
				&& (agendamento.getMedico() == null || !agendamento.getMedico().getId().equals(medico.getId()))) {

			agendamento.setMedico(medico);
		}
		if (agendamentoDTO.getPaciente() != null && (agendamento.getPaciente() == null
				|| !agendamento.getPaciente().getId().equals(agendamentoDTO.getPaciente()))) {
			final Paciente paciente = pacienteRepository.findById(agendamentoDTO.getPaciente())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "paciente not found"));
			agendamento.setPaciente(paciente);
		}
		return agendamento;
	}

}
