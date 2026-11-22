# AI-Assisted Development Notes

This project was prepared with AI assistance. This document should be reviewed and edited by the developer to accurately reflect their actual workflow before submission.

## Where AI was used
- Initial project structure and Maven dependency selection.
- Drafting the entity, DTOs, repository, service, controller and exception handling.
- Drafting a composable search/filter implementation.
- Preparing initial unit tests and technical documentation.

## How output should be validated
- Run `mvn clean test` and inspect all failures.
- Run the service against a local MySQL instance.
- Exercise every endpoint with Postman or curl, including invalid and duplicate data.
- Inspect generated SQL and database constraints.
- Review dependency versions and security configuration.
- Read and understand every class before submitting.

## Developer review and changes
Before submission, record the actual changes made after reviewing generated code, including any rejected recommendations and the reason. Do not claim validation steps that were not performed.

## Known limitations
The starter does not include authentication, integration tests against MySQL, database migrations, or production secrets management. These should be discussed as future work or implemented if time permits.
