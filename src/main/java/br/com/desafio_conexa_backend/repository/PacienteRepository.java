package br.com.desafio_conexa_backend.repository;

import br.com.desafio_conexa_backend.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
