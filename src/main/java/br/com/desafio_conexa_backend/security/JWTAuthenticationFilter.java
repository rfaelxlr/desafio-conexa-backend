package br.com.desafio_conexa_backend.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio_conexa_backend.domain.Medico;
import br.com.desafio_conexa_backend.domain.dto.CredentialsDTO;
import br.com.desafio_conexa_backend.repository.MedicoRepository;
import br.com.desafio_conexa_backend.service.AgendamentoService;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;
	
	private ObjectMapper mapper;

	private MedicoRepository medicoRepository;
	
	private AgendamentoService agendamentoService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil,
			MedicoRepository medicoRepository,ObjectMapper mapper, AgendamentoService agendamentoService) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.medicoRepository = medicoRepository;
		this.mapper = mapper;
		this.agendamentoService= agendamentoService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			CredentialsDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredentialsDTO.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getUsuario(),
					creds.getSenha(), new ArrayList<>());

			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String username = ((UserSS) auth.getPrincipal()).getUsuario();
		Long id = ((UserSS) auth.getPrincipal()).getId();

		Medico medico = medicoRepository.findByUsuario(username);

		String token = jwtUtil.generateToken(username, id);
		
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		Map<String, Object> json = new HashMap<>();
		json.put("token", token);
		json.put("medico", medico.getMedico());
		json.put("especialidade", medico.getEspecialidade());
		json.put("agendamentos_hoje", agendamentoService.findAllById(id));

   
        mapper.writeValue(res.getWriter(), json);
		//res.getWriter().write(json);
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
		}
	}
}
