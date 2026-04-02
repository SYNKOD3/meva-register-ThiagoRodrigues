# Projeto Meva Finance

Este projeto é uma API de gerenciamento financeiro desenvolvida com Spring Boot 3.4.3, focada no módulo REGISTER (Registro de Usuários). Ele serve como a base para o ecossistema Meva, garantindo a integridade dos dados através de validações rigorosas e tratamento global de exceções.

# Pré Requisitos

Para que seja possível rodar essa aplicação é necessário atender alguns requisitos básicos.

- Java 21 (LTS)
- Maven 3.3+
- PostgreSQL 15+ (ou via Docker)

# Compilando e inicializando

Assim como todo projeto *Maven*, é necessário primeiramente realizarmos a geração dos fontes. Conforme o exemplo abaixo:

```bash
mvn clean install
```

O projeto utiliza YAML para configurações e o Lombok para produtividade. Certifique-se de configurar o seu banco de dados PostgreSQL no arquivo `src/main/resources/application.yml`.

Certifique-se de que o repositório do Maven está corretamente configurado. Após os fontes terem sido gerados, basta executar
o comando abaixo para inicializar a aplicação:

```
$ java -jar target/meva-finance-0.0.1-SNAPSHOT.jar
```

Ou, se tiver importado por meio do IntelliJ, via classe `Application.java`. Para validar se a aplicação inicializou com
sucesso é necessario chamar o endpoint do *actuator* através do *link* abaixo:

```
http://localhost:8080/actuator/health
```

# Mostrando endpoints expostos

Assim que o projeto estiver sendo executado, é possível verificar as APIs expostas acessando a URL:

```
http://localhost:8080/swagger-ui.html
```

# Documentação

Abaixo uma lista de informações relevantes para iniciar o projeto:

## start

- Em caso de utilização local, configure o PostgreSQL e execute via Maven. 
- [Documentação de referência](https://spring.io/projects/spring-boot)

## Arquitetural

- Modelo arquitetural baseado em REST, utilizando Spring Data JPA para persistência e Bean Validation para integridade dos dados.

![image](https://user-images.githubusercontent.com/38960317/174696081-5e271e30-7aac-4dae-a33d-b3e9b64f79a7.png)

- Stack de tecnologias: Java 21, Spring Boot 3.4.3, PostgreSQL, Lombok, SpringDoc OpenAPI (Swagger).

- Scaffolding: Baseado no Spring Initializr com estrutura de camadas (Controller, Service, Repository).

## Package para produção

- Para gerar a versão de produção (JAR executável), utilize o comando: `mvn clean package -DskipTests`. O arquivo será gerado na pasta `/target`.

