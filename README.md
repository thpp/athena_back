# Athena Backend
[![Ask DeepWiki](https://devin.ai/assets/askdeepwiki.png)](https://deepwiki.com/thpp/athena_back.git)

Athena is a Spring Boot application designed to manage students (`alunos`) and their projects (`projetos`). It features a modular, CQRS-style architecture that separates write operations (commands) from read operations (queries), promoting a clean and scalable design.

## Architecture

The project is organized into three primary Maven modules, each with a distinct responsibility:

*   **`core`**: This module contains the domain logic, following principles of Domain-Driven Design (DDD). It defines the main aggregates (`Aluno`, `Projeto`), value objects (`AlunoId`, `ProjetoId`), repositories, and use cases for handling commands (e.g., `RegistrarAlunoUseCase`). This represents the "write" side of the application.
*   **`query`**: This module is dedicated to the "read" side of the application. It includes lightweight data transfer objects (projections like `Aluno`), query-specific models (`AlunoQuery`), and repositories optimized for fetching data efficiently without the overhead of the domain model.
*   **`app`**: The main application module that integrates the `core` and `query` modules. It contains the Spring Boot application entry point, REST controllers for exposing the API, and all necessary configuration, including database settings and migration paths.

## Technologies Used

*   **Backend**: Java 21, Spring Boot 3.2
*   **Data**: Spring Data JPA, Hibernate, PostgreSQL
*   **Database Migration**: Flyway
*   **Build**: Apache Maven
*   **Development**: Lombok, Docker

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 21
*   Docker and Docker Compose

### Setup & Installation

1.  **Clone the repository:**
    ```sh
    git clone <repository_url>
    cd athena_back
    ```

2.  **Start the Database:**
    The project includes a Docker Compose configuration for running a local PostgreSQL database.
    ```sh
    cd devops/docker/local
    docker-compose up -d
    ```
    This will start a PostgreSQL container accessible on `localhost:5433`. The database `athena-db` will be created automatically.

3.  **Run the Application:**
    Navigate back to the root directory and use the Maven wrapper to launch the application.
    ```sh
    ./mvnw spring-boot:run
    ```
    The application will start and connect to the database. By default, it runs on `http://localhost:8080`.

## API Endpoints

The API is versioned and accessible under the `/api/v1/` path.

### Alunos (Students)

*   **Register a new student**
    *   **Endpoint**: `POST /api/v1/alunos`
    *   **Request Body**:
        ```json
        {
          "nome": "John Doe",
          "matricula": "202412345"
        }
        ```
    *   **Success Response**: `201 Created` with a `Location` header pointing to the query endpoint for the newly created resource.

*   **Get a student by ID**
    *   **Endpoint**: `GET /api/v1/alunos/{id}`
    *   **Example**: `GET /api/v1/alunos/123e4567-e89b-12d3-a456-426614174000`
    *   **Success Response**: `200 OK` with the student's data.
        ```json
        {
          "id": "123e4567-e89b-12d3-a456-426614174000",
          "matricula": "202412345"
        }
        ```

### Projetos (Projects)

*   **Register a new project**
    *   **Endpoint**: `POST /api/v1/projetos`
    *   **Request Body**:
        ```json
        {
          "nomeProjeto": "Athena Project",
          "nomeProfessor": "Dr. Smith"
        }
        ```
    *   **Success Response**: `201 Created` with a `Location` header.

## Database Migrations

Database schema management is handled by Flyway. Migration scripts are located in `app/src/main/resources/db/migration/v1`. Flyway automatically applies pending migrations upon application startup.

The initial migrations create two tables:
*   `aluno`: Stores student information, including ID, name, and a unique registration number (`matricula`).
*   `projeto`: Stores project details, including ID, project name, and professor name.
