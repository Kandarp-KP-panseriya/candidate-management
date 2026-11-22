# Technical Design

## Scope
A small REST API for creating, viewing, updating, deleting, searching and filtering candidate records.

## Architecture
The application uses a layered monolith:
- Controller: HTTP mapping, request validation and response codes.
- Service: business rules, duplicate-email checks and transaction boundaries.
- Repository: Spring Data JPA persistence.
- Specification: composable optional search predicates.
- DTO: API contract separated from the database entity.
- Controller advice: consistent validation and application errors.

A modular monolith is appropriate for the current assignment size. Microservices would add deployment and operational complexity without a demonstrated need.

## Data model
`Candidate` stores identity/contact information, skills, experience, current company,
notice period and audit timestamps. Email is unique. Name and experience indexes
support common filters.

## API behaviour
- POST returns 201.
- GET returns 200 or 404.
- PUT returns 200 or 404/409.
- DELETE returns 204 or 404.
- Invalid request bodies return 400.
- Duplicate email returns 409.
- List/search is paginated.

## Scalability evolution
1. Keep pagination mandatory and tune indexes using real query plans.
2. Add database migrations and connection-pool tuning.
3. Add caching only for measured read-heavy use cases; invalidate on writes.
4. Introduce asynchronous events/queues for slow integrations such as email or resume parsing.
5. Add observability: structured logs, metrics, tracing and health checks.
6. Scale stateless API instances horizontally behind a load balancer.
7. For very large datasets, evaluate read replicas, partitioning and dedicated search (e.g. OpenSearch) based on measured bottlenecks.
8. Add authentication/authorization, rate limiting, audit history and PII retention controls before production.

## Trade-offs and limitations
This is an assignment-sized starter, not production-hardened software. It does not include authentication, resume file storage, full-text search infrastructure, migration scripts, or end-to-end tests against a real MySQL instance. Hibernate schema update is used for local convenience only.
