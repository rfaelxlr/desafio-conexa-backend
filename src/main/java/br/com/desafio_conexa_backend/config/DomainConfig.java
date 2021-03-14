package br.com.desafio_conexa_backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan(basePackages = "br.com.desafio_conexa_backend.domain")
@EnableJpaRepositories(basePackages = "br.com.desafio_conexa_backend.repository")
@EnableTransactionManagement
public class DomainConfig {
}
