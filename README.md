# Raízes do Nordeste - API

API backend para o sistema de e-commerce e gestão da rede Raízes do Nordeste.

## 🚀 Como Rodar o Projeto

### 1. Pré-requisitos

- **Java 21** (ou superior)
- **Maven 3.9+**
- **Docker** (recomendado) ou uma instância local do **PostgreSQL 15+**

### 2. Variáveis de Ambiente (`.env.example`)

As configurações da aplicação estão no arquivo `src/main/resources/application.properties`. Não há necessidade de um arquivo `.env`. As variáveis principais são:

```properties
# PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/raizes
DB_USER=postgres
DB_PASSWORD=senha

# JWT
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
```
*O projeto já usa esses valores como padrão, então você não precisa alterá-los para o ambiente de desenvolvimento.*

### 3. Configuração do Banco de Dados

**Usando Docker (Recomendado):**
Execute o comando abaixo no seu terminal para subir um container PostgreSQL com as configurações esperadas pela API:
```bash
docker run --name raizes-postgres -e POSTGRES_DB=raizes -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=senha -p 5432:5432 -d postgres:15
```

### 4. Executando a Aplicação

1.  **Build do Projeto:**
    ```bash
    mvn clean install
    ```
2.  **Executar a Aplicação:**
    ```bash
    mvn spring-boot:run
    ```
A API estará disponível em `http://localhost:8080`.

### 5. Migrations e Seed

- **Migrations:** O projeto usa `spring.jpa.hibernate.ddl-auto=update`. O Hibernate criará e atualizará as tabelas automaticamente com base nas entidades Java. Não há migrations manuais.
- **Seed:** Não há scripts de seed. Os dados iniciais (como Categorias, Unidades, etc.) devem ser inseridos via API.

### 6. Documentação da API (Swagger)

Com a aplicação rodando, acesse a documentação interativa no seu navegador:
- **URL:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### 7. Coleção de Testes (Postman)

1.  **Importar a Coleção:**
    - Abra o Postman e vá em `File > Import...`.
    - Selecione o arquivo `raizes-backend.postman_collection.json` que está na raiz deste repositório.

2.  **Configurar Variáveis:**
    - A coleção usa a variável `{{baseUrl}}`, que já vem pré-configurada como `http://localhost:8080`.
    - Após fazer login (`/auth/login`), copie o token da resposta e cole na variável `{{token}}` da coleção para autenticar as requisições protegidas.
