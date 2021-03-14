package br.com.desafio_conexa_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.desafio_conexa_backend.domain.Medico;


public interface MedicoRepository extends JpaRepository<Medico, Long> {
	
	@Transactional(readOnly = true)
	Medico findByUsuario(String usuario);
}
