# Candidate Management API

A Java 17 / Spring Boot REST API for managing recruitment candidates.

## Stack
- Java 17
- Spring Boot 3.3.5
- Spring Web, Spring Data JPA, Bean Validation
- MySQL 8
- Maven
- JUnit 5 and Mockito

## Run locally

### Requirements
Install JDK 17, Maven 3.8+ and Docker (or MySQL 8).

### Start MySQL with Docker
```bash
docker compose up -d
```
This starts MySQL on port 3306 with database `candidate_management`,
username `root`, password `root`.

Alternatively, create the database yourself and configure `DB_URL`, `DB_USERNAME`,
and `DB_PASSWORD` environment variables.

### Run the application
```bash
mvn clean test
mvn spring-boot:run
```
Base URL: `http://localhost:8080/api/v1/candidates`

> For production, do not use the sample root password or Hibernate `ddl-auto=update`.
> Use secrets/environment variables and a migration tool such as Flyway.

## API

### Create candidate
`POST /api/v1/candidates`
```json
{
  "firstName": "Asha",
  "lastName": "Patel",
  "email": "asha@example.com",
  "phone": "+91 9876543210",
  "skills": "Java, Spring Boot, MySQL",
  "experienceYears": 2.5,
  "currentCompany": "Acme Technologies",
  "noticePeriodDays": 30
}
```

### Get candidate
`GET /api/v1/candidates/{id}`

### Update candidate
`PUT /api/v1/candidates/{id}` (send the complete candidate request body)

### Delete candidate
`DELETE /api/v1/candidates/{id}`

### Search and filter
`GET /api/v1/candidates?q=java&minExperience=1&maxExperience=5&company=acme&page=0&size=20&sort=createdAt,desc`

All query filters are optional. Search term checks first name, last name, email, skills and company.
Results are paginated.

## Example error response
```json
{
  "timestamp": "2026-01-01T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "validationErrors": {
    "email": "must be a well-formed email address"
  }
}
```

## Design notes
- Controller / service / repository layering separates HTTP, business logic and persistence.
- Request and response DTOs prevent direct exposure of persistence entities.
- Specifications combine optional search filters without creating many repository methods.
- Pagination bounds result size and supports sorting.
- Unique email is checked in the service and enforced by a database unique constraint.
- `@Transactional` defines service transaction boundaries.
- Indexes are included for common name and experience queries.

## Testing
Run `mvn test`. Unit tests cover duplicate-email handling and retrieval; the Spring context test checks application wiring.
Add integration tests against a disposable MySQL/Testcontainers instance before production use.
