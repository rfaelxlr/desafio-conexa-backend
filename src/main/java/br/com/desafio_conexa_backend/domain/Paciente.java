package br.com.desafio_conexa_backend.domain;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Paciente {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String nome;

    @Column( unique = true)
    private String cpf;

    @Column()
    private String idade;

    @Column()
    private String telefone;

    @JsonIgnore
    @OneToMany(mappedBy = "paciente",cascade = CascadeType.ALL)
    private Set<Agendamento> pacienteAgendamentos;
    @JsonIgnore
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;
    @JsonIgnore
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

	public Paciente(Long id, String nome, String cpf, String idade, String telefone) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.idade = idade;
		this.telefone = telefone;
	}
    
    

}
