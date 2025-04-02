# SpringBank

**SpringBank** é uma aplicação backend desenvolvida em Java com Spring Boot, projetada para gerenciar autenticação de usuários e operações bancárias, como login, registro, depósitos, saques e transferências. Este projeto foi desenvolvido para estudos e pode ser utilizado como base para um sistema de banco digital.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.4**
- **Spring Security**
- **BCrypt** (para criptografia de senhas)
- **JWT** (JSON Web Token para autenticação)
- **MongoDB** (banco de dados NoSQL)
- **Lombok** (redução de código boilerplate)
- **Javax Validation API** (para validação de dados)
- **Java JWT** (para manipulação de tokens JWT)

---

## Configuração do Ambiente

A aplicação utiliza o arquivo `application.properties` para configurar parâmetros essenciais:

```properties
spring.application.name=SpringBank

spring.data.mongodb.uri=mongodb://localhost:27017/SpringBank

api.security.token.secret=${JWT_SECRET:my-secret-key}

    spring.application.name: Nome da aplicação.

    spring.data.mongodb.uri: URI para conexão com o MongoDB.

    api.security.token.secret: Chave secreta para assinatura dos tokens JWT (pode ser sobrescrita pela variável de ambiente JWT_SECRET).

Estrutura do Projeto
Ponto de Entrada

A classe principal é SpringBankApplication, localizada no pacote dev.jr.SpringBank, responsável por iniciar a aplicação Spring Boot:

package dev.jr.SpringBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "dev.jr.SpringBank") // Escaneia todos os componentes do projeto
public class SpringBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBankApplication.class, args);
    }
}

Gerenciamento de Dependências

O projeto utiliza o Maven para gerenciamento de dependências. Abaixo, um trecho do pom.xml com as principais dependências:

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.validation</groupId>
        <artifactId>validation-api</artifactId>
        <version>2.0.1.Final</version>
    </dependency>
    <dependency>
        <groupId>com.auth0</groupId>
        <artifactId>java-jwt</artifactId>
        <version>4.5.0</version>
    </dependency>
</dependencies>

Executando o Projeto
Pré-requisitos

    Java 21 instalado.

    Maven instalado.

    MongoDB em execução e configurado conforme o arquivo application.properties.

Passos

    Clone o repositório:

git clone https://github.com/seu-usuario/SpringBank.git

Acesse o diretório do projeto:

cd SpringBank

Configure o banco de dados:
Certifique-se de que o MongoDB está ativo e configurado conforme o arquivo de propriedades.

Compile e execute a aplicação:

    mvn spring-boot:run

Endpoints da API
Autenticação

    Login
    POST /auth/login
    Corpo da requisição:

{
  "login": "usuario",
  "password": "senha"
}

Resposta:

{
  "token": "jwt-token-gerado"
}

Registro
POST /auth/register
Corpo da requisição:

    {
      "login": "usuario",
      "password": "senha",
      "name": "Nome Completo",
      "email": "email@exemplo.com",
      "cpf": "12345678900"
    }

    Respostas:

        200 OK: Registro bem-sucedido.

        400 Bad Request: Login já existente.

Gerenciamento de Usuários

    Atualizar Login
    PUT /users/changeUserLogin
    Parâmetros: currentLogin, newUserLogin
    Resposta: Login atualizado com sucesso.

    Atualizar E-mail
    PUT /users/changeUserEmail
    Parâmetros: login, newUserEmail
    Resposta: E-mail atualizado com sucesso.

    Atualizar Senha
    PUT /users/changeUserPassword
    Parâmetros: login, newUserPassword
    Resposta: Senha atualizada com sucesso.

    Atualizar Nome Completo
    PUT /users/changeUserName
    Parâmetros: login, newUserName
    Resposta: Nome atualizado com sucesso.

    Excluir Usuário
    DELETE /users/delete/{login}
    Respostas:

        200 OK: Usuário deletado com sucesso.

        403 Forbidden: Exclusão permitida apenas para a própria conta.

        404 Not Found: Usuário não encontrado.

Operações Bancárias

    Depósito
    PUT /users/deposit/{login}/{value}
    Resposta: Depósito de R$ {value} realizado com sucesso.

    Saque
    PUT /users/withdraw/{login}/{value}
    Resposta: R$ {value} sacado com sucesso.

    Transferência
    POST /users/transfer/{originLogin}/{destinationLogin}/{value}
    Resposta: R$ {value} transferido com sucesso.

Validação de Dados

Os DTOs do projeto possuem regras de validação para garantir a integridade dos dados:

    AuthDTO: Garante que login e senha não sejam nulos ou vazios.

    RegisterDTO: Valida os campos obrigatórios (login, senha, nome, e-mail e CPF).

    LoginResponseDTO: Retorna um token JWT válido para autenticação.

Configuração de Segurança

A aplicação utiliza Spring Security para gerenciar autenticação e autorização, com as seguintes configurações principais:

    Autenticação Stateless: Sem uso de sessões.

    Proteção CSRF Desativada: Adequada para APIs REST.

    Regras de Permissão:

        Login e Registro: Acesso público.

        Exclusão e Modificação de Usuários: Acesso restrito aos usuários autenticados (com papéis ADMIN ou USER para alterações nos próprios dados).

        Outras Requisições: Requerem autenticação.

Além disso, um filtro de segurança intercepta as requisições para validar tokens JWT e autenticar os usuários.
Contribuição

Contribuições são bem-vindas! Se você deseja melhorar o projeto, siga estes passos:

    Faça um fork do repositório.

    Crie uma branch para sua feature ou correção.

    Envie um Pull Request com suas alterações.
```
