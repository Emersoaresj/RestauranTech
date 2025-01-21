
# RestauranTech - Sistema de Gerenciamento de Restaurantes

**RestauranTech** é um sistema desenvolvido com o objetivo de gerenciar as operações de restaurantes, incluindo o gerenciamento de usuários, cadastros, atualizações e autenticação. O projeto foi desenvolvido como parte do curso de Pós-Graduação em **Arquitetura e Desenvolvimento em JAVA** da **FIAP**.

O sistema permite a criação, atualização e exclusão de usuários, além de incluir funcionalidades para validar login e alterar senhas de forma segura.

## Tecnologias utilizadas até o momento:

- **Spring Boot**: Framework utilizado para o desenvolvimento da API.
- **PostgreSQL**: Banco de dados relacional para persistência de dados.
- **Spring Data JPA**: Para facilitar o acesso ao banco de dados.
- **Docker**: Utilizado para containerização da aplicação e banco de dados.

## Objetivo do Projeto

Criar um sistema robusto que permita a todos os restaurantes gerenciar suas operações de forma eficiente, enquanto os clientes podem consultar informações, deixar avaliações e fazer pedidos online.

## Como Executar Localmente

### Requisitos

- **Java 17+**
- **Docker** (para rodar a aplicação e o banco de dados PostgreSQL)
- **Postman** (para realizar os testes da API)

### Passos para Execução

1. Clone o repositório:
   ```bash
   git clone https://github.com/Emersoaresj/RestauranTech.git
   cd RestauranTech
   ```

2. **Suba os containers Docker**:
   - O arquivo `docker-compose.yml` está configurado para rodar a aplicação e o PostgreSQL. Execute o seguinte comando:
     ```bash
     docker-compose up --build
     ```

3. A aplicação estará disponível em `http://localhost:8080`.

4. O banco de dados estará disponível em `localhost:5432` com o usuário e senha configurados no `docker-compose.yml`.

5. **Testando a API com o Postman**:
   - Você pode importar a coleção do Postman a partir do link abaixo para testar todos os endpoints da API.
[Link para a Collection do Postman](https://github.com/Emersoaresj/collections-PosTech/blob/main/RestauranTech.postman_collection.json)

### Endpoints Principais

- **POST** `/cadastrar` - Cadastrar um novo usuário.
- **PUT** `/{id}` - Atualizar dados de um usuário.
- **POST** `/alterar-senha/{id}` - Alterar a senha de um usuário.
- **POST** `/validar-login` - Validar o login de um usuário.
- **DELETE** `/{id}` - Deletar um usuário.

## Estrutura do Projeto

- **src/main/java/br/com/fiap/restaurantesgerenciamento**:
  - **controller**: Contém os endpoints da API.
  - **model**: Contém as entidades de dados.
  - **service**: Lógica de negócios.
  - **repository**: Repositórios JPA para acesso ao banco de dados.

- **docker-compose.yml**: Arquivo para inicialização do PostgreSQL e da aplicação.
- **application.properties**: Configurações da aplicação (Banco de dados, segurança, etc.).


## Contato

Se você tiver dúvidas ou sugestões, pode entrar em contato:

- **Emerson Soares** - [emersonsoares269@icloud.com](mailto:emersonsoares269@icloud.com)
