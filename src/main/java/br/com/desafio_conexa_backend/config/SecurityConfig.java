package br.com.desafio_conexa_backend.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.desafio_conexa_backend.repository.MedicoRepository;
import br.com.desafio_conexa_backend.security.JWTAuthenticationFilter;
import br.com.desafio_conexa_backend.security.JWTAuthorizationFilter;
import br.com.desafio_conexa_backend.security.JWTUtil;
import br.com.desafio_conexa_backend.service.AgendamentoService;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	

	@Autowired
	private Environment env;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private AgendamentoService agendamentoService;
	

	
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**",
			"/api/pacientes/**",
			"/swagger-ui/**",
			"/swagger-ui.html/**",
			"/v3/api-docs/**"

	};
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }
		
		http.cors().and().csrf().disable().authorizeRequests()
			.antMatchers( PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil,medicoRepository,mapper,agendamentoService));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.headers().frameOptions().disable();

		
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	} 
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
