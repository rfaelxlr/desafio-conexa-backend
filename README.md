# Desafio-conexa-backend

### Tabela de conteúdos

<!--ts-->
   * [Sobre](#Sobre)
   * [Configuração inicial](#Configuração-inicial)
   * [Como Rodar](#Como-rodar)
   * [Features](#Features)
   * [Tecnologias](#tecnologias)
<!--te-->

### Sobre
Projeto de desafio técnico de backend para a Conexa Saúde. Segue a [Descrição do desafio](https://gitlab.com/conexasaude-public/desafio-tecnico-backend-conexa)

### Configuração inicial
O projeto pode ser iniciado com o banco de dados Mysql ou H2. 
</br>
</br>
Para configurar as credenciais do banco altere em src/main/resources no arquivo "application.properties". 
</br>
 * H2: Em "spring.profiles.active" mude o valor para "test".
 * Mysql: Em "spring.profiles.active" mude o valor para "dev".
    * Em "application-dev.properties": Altere as credenciais do banco de dados


```
spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco?useSSL=false&serverTimezone=UTC
spring.datasource.username=usario
spring.datasource.password=senha

```
* Base: Rode a primeira vez com a configuração de criar a base:
```
spring.jpa.hibernate.ddl-auto=create

```
Em seguida mude para update:
```
spring.jpa.hibernate.ddl-auto=update

```


### Como rodar

```bash
# Clone este repositório
$ git clone <https://github.com/rfaelxlr/desafio-conexa-backend>

# Acesse a pasta do projeto no terminal/cmd
$ cd desafio-conexa-backend

# Digite o comando
$ mvn spring-boot:run



# O servidor inciará na porta:8080 - acesse <http://localhost:8080>
# Para encontrar as documentação das rotas - acesse <http://localhost:8080/swagger-ui.html>
# OBS: Rota de login está em <http://localhost:8080/login>
```

- Autenticação base:

```
{
  "usuario": "medico@email.com",
  "senha": "medico123"
}

```

### Features

- [x] CRUD de Pacientes
- [x] Autenticação dos Médicos retornando a agenda do dia
- [X] Consulta e cadastro de agendamento

### Tecnologias
As seguintes ferramentas foram usadas na construção do projeto:

- Java 1.8
- Spring boot 2.4.3
- Spring security JWT
- Mysql
- H2
