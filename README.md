# Raízes do Nordeste - API

API backend para o sistema de e-commerce e gestão da rede Raízes do Nordeste.

## 🚀 Como Rodar o Projeto

### 1. Pré-requisitos

- **Java 21** (ou superior)
- **Maven 3.9+**
- **Docker** (recomendado) ou uma instância local do **PostgreSQL 15+**

### 2. Variáveis de Ambiente

As configurações da aplicação estão no arquivo `src/main/resources/application.properties` e podem ser sobrescritas por variáveis de ambiente. Use `.env.example` apenas como referência; não suba arquivos `.env` reais para o GitHub.

```properties
# PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/raizes
DB_USER=postgres
DB_PASSWORD=senha

# JWT
JWT_SECRET=UmFpemVzTm9yZGVzdGVEZXZlbG9wbWVudFNlY3JldEtleTIwMjY=
JWT_EXPIRATION=86400000
```
*O projeto usa valores padrão de desenvolvimento. Em produção, configure valores próprios por variável de ambiente.*

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
A API estará disponível em `http://localhost:8081`.

### 5. Migrations e Seed

- **Migrations:** O projeto usa `spring.jpa.hibernate.ddl-auto=update`. O Hibernate criará e atualizará as tabelas automaticamente com base nas entidades Java. Não há migrations manuais.
- **Seed:** Não há scripts de seed. Os dados iniciais (como Categorias, Unidades, etc.) devem ser inseridos via API.

### 6. Documentação da API (Swagger)

Com a aplicação rodando, acesse a documentação interativa no seu navegador:
- **URL:** [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

### 7. Coleção de Testes (Postman)

1.  **Importar a Coleção:**
    - Abra o Postman e vá em `File > Import...`.
    - Selecione o arquivo `raizes-backend.postman_collection.json` que está na raiz deste repositório.

2.  **Configurar Variáveis:**
    - A coleção usa a variável `{{baseUrl}}`, que já vem pré-configurada como `http://localhost:8081`.
    - Após fazer login (`/auth/login`), copie o token da resposta e cole na variável `{{token}}` da coleção para autenticar as requisições protegidas.
