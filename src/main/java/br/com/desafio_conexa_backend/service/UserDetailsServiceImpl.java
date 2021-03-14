package br.com.desafio_conexa_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.desafio_conexa_backend.domain.Medico;
import br.com.desafio_conexa_backend.repository.MedicoRepository;
import br.com.desafio_conexa_backend.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MedicoRepository repo;
	
	

	@Override
	public UserDetails loadUserByUsername(String usuario) throws UsernameNotFoundException {
		Medico medico = repo.findByUsuario(usuario);
		if (medico == null) {
			throw new UsernameNotFoundException(usuario);
		}
		return new UserSS(medico.getId(), medico.getUsuario(), medico.getSenha());
	}

	

}
