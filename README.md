# Authentication demo

This is a demo authentication application I have been steadily working on since 2020. There is a high chance I will make the repository private again in the next few months, hence this readme is not very thorough and does not include deployment instructions. I created the application to experiment with the following concepts:

- Using authentication tokens with isomorphic web front-ends,
- Stateless/RESTful Mobile-ID and Smart-ID authentication.

## Structure

The repository consists of the following projects:

- project-x-ui - Isomorphic front-end built with Next.js
- auth-api - Microservice for authentication token management (and in the future for authorization)
- account-api - Microservice for account management
- password-api - Microservice for the password authentication method
- mobile-id-api - Microservice for the Mobile-ID authentication method
- smart-id-api - Microservice for the Smart-ID authentication method
- auth-api-client - REST client library for auth-api, used by password-api, mobile-id-api and smart-id-api
- account-api-client - REST client library for account-api, used by password-api, mobile-id-api and smart-id-api
- rest-common - Base library for REST client libraries, used by auth-api-client and account-api-client

## Future plans

- Estonian ID-Card authentication
- Multiple authentication methods per account
- Authorization (roles and privileges)
