package br.com.desafio_conexa_backend.domain;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Medico {

	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String medico;

	@Column(nullable = false, unique = true)
	private String usuario;

	@Column(nullable = false)
	private String senha;

	@Column
	private String especialidade;

	@OneToMany(mappedBy = "medico", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Agendamento> medicoAgendamentos;

	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;

	@Column(nullable = false)
	private OffsetDateTime lastUpdated;

	@PrePersist
	public void prePersist() {
		dateCreated = OffsetDateTime.now();
		lastUpdated = dateCreated;
	}

	@PreUpdate
	public void preUpdate() {
		lastUpdated = OffsetDateTime.now();
	}

	public Medico(Long id, String medico, String usuario, String senha, String especialidade) {
		this.id = id;
		this.medico = medico;
		this.usuario = usuario;
		this.senha = senha;
		this.especialidade = especialidade;
	}

}
