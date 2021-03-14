package br.com.desafio_conexa_backend.service;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.desafio_conexa_backend.domain.Agendamento;
import br.com.desafio_conexa_backend.domain.Medico;
import br.com.desafio_conexa_backend.domain.Paciente;
import br.com.desafio_conexa_backend.repository.AgendamentoRepository;
import br.com.desafio_conexa_backend.repository.MedicoRepository;
import br.com.desafio_conexa_backend.repository.PacienteRepository;

@Service
public class DBService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private MedicoRepository medicoRepository;

	@Autowired
	private PacienteRepository pacienteRepository;
	@Autowired
	BCryptPasswordEncoder pe;

	public void instantiateTestDatabase() throws ParseException {
		
		Medico medico = new Medico(1L, "Rafael Lima", "medico@email.com", pe.encode("medico123"), "Cardiologia");
		Medico medico2 = new Medico(2L, "Rafael Lima 2 ", "medico2@email.com", pe.encode("medico123"), "Pediatria");
		Medico medico3 = new Medico(3L, "Rafael Lima 3", "medico3@email.com", pe.encode("medico123"), "Homeopatia");
		
		medicoRepository.saveAll(Arrays.asList(medico,medico2,medico3));
		
		Paciente paciente = new Paciente(1l,"Paciente","121.553.444-22","25","(81) 997937392");
		Paciente paciente2 = new Paciente(2l,"Paciente 2","121.553.444-21","22","(81) 997937392");
		Paciente paciente3 = new Paciente(3l,"Paciente 3","121.553.444-23","21","(81) 997937392");
		Paciente paciente4 = new Paciente(4l,"Paciente 4","121.553.444-24","18","(81) 997937392");
		Paciente paciente5 = new Paciente(5l,"Paciente 5","121.553.444-25","35","(81) 997937392");

		pacienteRepository.saveAll(Arrays.asList(paciente,paciente2,paciente3,paciente4,paciente5));
		
		
		Agendamento agendamento = new Agendamento(1l,"2021-03-14 10:30:00",medico,paciente);
		Agendamento agendamento2 = new Agendamento(2l,"2021-03-14 11:00:00",medico,paciente2);
		Agendamento agendamento3= new Agendamento(3l,"2021-03-15 08:00:00",medico,paciente4);
		Agendamento agendamento4= new Agendamento(4l,"2021-03-15 10:30:00",medico,paciente3);
		Agendamento agendamento5= new Agendamento(5l,"2021-03-15 07:30:00",medico2,paciente5);


		agendamentoRepository.saveAll(Arrays.asList(agendamento,agendamento2,agendamento3,agendamento4,agendamento5));

	}
}
