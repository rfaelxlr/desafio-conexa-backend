package br.com.desafio_conexa_backend.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio_conexa_backend.domain.Medico;
import br.com.desafio_conexa_backend.repository.MedicoRepository;
import br.com.desafio_conexa_backend.security.JWTUtil;
import br.com.desafio_conexa_backend.security.UserSS;
import br.com.desafio_conexa_backend.service.AgendamentoService;
import br.com.desafio_conexa_backend.service.UserServiceSS;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private JWTUtil jwtUtil;
	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private MedicoRepository medicoRepository;
	@Autowired
	private AgendamentoService agendamentoService;

	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS medicoAuth = UserServiceSS.authenticated();
		Medico medico = medicoRepository.findByUsuario(medicoAuth.getUsuario());

		String token = jwtUtil.generateToken(medicoAuth.getUsuario(), medicoAuth.getId());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> json = new HashMap<>();
		json.put("token", token);
		json.put("medico", medico.getMedico());
		json.put("especialidade", medico.getEspecialidade());
		json.put("agendamentos_hoje", agendamentoService.findAllById(medicoAuth.getId()));

		try {
			mapper.writeValue(response.getWriter(), json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.noContent().build();
	}

}