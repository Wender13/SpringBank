# SpringBank

SpringBank é uma aplicação backend desenvolvida em Java com Spring Boot para gerenciamento de autenticação de usuários, incluindo login, registro e operações bancárias.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Security**
- **BCrypt** (para criptografia de senhas)
- **JWT** (JSON Web Token para autenticação)
- **MongoDB** (banco de dados NoSQL)

## Instalação e Execução

1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/SpringBank.git
   ```
2. Acesse o diretório do projeto:
   ```sh
   cd SpringBank
   ```
3. Configure o banco de dados MongoDB no `application.properties` ou `application.yml`.
4. Compile e execute a aplicação:
   ```sh
   mvn spring-boot:run
   ```

## Endpoints

### Autenticação

#### Login

**POST** `/auth/login`

**Corpo da requisição:**

```json
{
  "login": "usuario",
  "password": "senha"
}
```

**Resposta:**

```json
{
  "token": "jwt-token-gerado"
}
```

#### Registro

**POST** `/auth/register`

**Corpo da requisição:**

```json
{
  "login": "usuario",
  "password": "senha",
  "name": "Nome Completo",
  "email": "email@exemplo.com",
  "cpf": "12345678900"
}
```

**Respostas:**

- `200 OK`: Registro bem-sucedido
- `400 Bad Request`: Login já existente

### Gerenciamento de Usuários

#### Atualizar login

**PUT** `/users/changeUserLogin`

**Parâmetros:** `currentLogin`, `newUserLogin`

**Resposta:** `Login atualizado com sucesso.`

#### Atualizar e-mail

**PUT** `/users/changeUserEmail`

**Parâmetros:** `login`, `newUserEmail`

**Resposta:** `E-mail atualizado com sucesso.`

#### Atualizar senha

**PUT** `/users/changeUserPassword`

**Parâmetros:** `login`, `newUserPassword`

**Resposta:** `Senha atualizada com sucesso.`

#### Atualizar nome completo

**PUT** `/users/changeUserName`

**Parâmetros:** `login`, `newUserName`

**Resposta:** `Nome atualizado com sucesso.`

#### Excluir usuário

**DELETE** `/users/delete/{login}`

**Resposta:**

- `200 OK`: Usuário deletado com sucesso.
- `403 Forbidden`: Você só pode excluir sua própria conta.
- `404 Not Found`: Usuário não encontrado.

### Operações Bancárias

#### Depósito

**PUT** `/users/deposit/{login}/{value}`

**Resposta:** `Depósito de R$ {value} realizado com sucesso.`

#### Saque

**PUT** `/users/withdraw/{login}/{value}`

**Resposta:** `R$ {value} sacado com sucesso.`

#### Transferência entre usuários

**POST** `/users/transfer/{originLogin}/{destinationLogin}/{value}`

**Resposta:** `R$ {value} transferido com sucesso.`

## Validação de Dados

Os seguintes DTOs possuem regras de validação para garantir a integridade dos dados:

- **AuthDTO**: O login e a senha não podem ser nulos ou vazios.
- **RegisterDTO**: Campos como login, senha, nome, e-mail e CPF devem ser informados corretamente.
- **LoginResponseDTO**: Retorna um token JWT válido para autenticação.

## Repositórios

### Repositório de Transações

O `TransactionRepository` é responsável pelo gerenciamento das transações no banco de dados MongoDB.

#### Métodos Disponíveis

- `findByUser(String userLogin)`: Retorna todas as transações onde o usuário de origem tem o login especificado.
- `findByBeneficiary(String beneficiaryLogin)`: Retorna todas as transações onde o usuário de destino tem o login especificado.

### Repositório de Usuários

O `UserRepository` é responsável pelo gerenciamento dos dados dos usuários no banco de dados MongoDB.

#### Métodos Disponíveis

- `findByCPF(String cpf)`: Busca um usuário pelo CPF.
- `findByLogin(String login)`: Retorna os detalhes de um usuário pelo login.
- `findByEmailAndPassword(String email, String password)`: Busca um usuário pelo e-mail e senha.
- `findByCPFAndPassword(String cpf, String password)`: Busca um usuário pelo CPF e senha.
- `findByEmailAndCPF(String email, String cpf)`: Retorna um usuário com base no e-mail e CPF.
- `findByEmail(String email)`: Retorna um usuário com base no e-mail.
- `deleteByLogin(String login)`: Exclui um usuário com base no login.

## Configuração de Segurança

A aplicação utiliza **Spring Security** para gerenciar autenticação e autorização.

### Principais Configurações

- **Autenticação Stateless**: Configurada para não utilizar sessões.
- **Proteção CSRF desativada**: Para facilitar a API REST.
- **Regras de Permissão:**
  - **Login e Registro**: Acesso público.
  - **Exclusão de Usuário**: Apenas usuários autenticados.
  - **Modificação de Usuários**: Apenas **ADMIN** e **USER** podem modificar seus próprios dados.
  - **Todas as outras requisições**: Requerem autenticação.

### Filtro de Segurança

O `SecurityFilter` intercepta requisições para validar tokens JWT e autenticar usuários. Se o token for válido, o usuário é autenticado no contexto de segurança.

#### Exemplo de Implementação

```java
@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    var token = this.recoverToken(request);
    if(token != null){
        var login = tokenService.validateToken(token);
        UserDetails user = userRepository.findByLogin(login);
        if(user != null){
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
    filterChain.doFilter(request, response);
}
```

## Dependências Maven

O projeto utiliza as seguintes bibliotecas principais no `pom.xml`:

- `spring-boot-starter-web`: Criação de APIs REST.
- `spring-boot-starter-security`: Autenticação e autorização com Spring Security.
- `spring-boot-starter-data-mongodb`: Integração com MongoDB.
- `java-jwt`: Geração e validação de tokens JWT.
- `lombok`: Geração automática de getters/setters e construtores.
- `validation-api`: Validação de dados de entrada.
- `javax.servlet-api`: Suporte a servlets.
- `spring-boot-starter-test` e `spring-security-test`: Testes automatizados.

## Contribuição

Sinta-se à vontade para contribuir! Forke o repositório, crie uma branch e abra um Pull Request.

## Licença

Este projeto está sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.
