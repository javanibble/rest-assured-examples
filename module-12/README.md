# Module 12: Real Infrastructure with Testcontainers

This module covers running REST Assured against real infrastructure in Docker, and making a large suite fast and organised: real OAuth 2.0 tokens from Keycloak, parallel execution, and tags with Maven profiles.

## Table of Contents

- [Module 12: Real Infrastructure with Testcontainers](#module-12-real-infrastructure-with-testcontainers)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Protected Accounts and Reference Data](#the-domain-protected-accounts-and-reference-data)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-12/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Real Tokens from Keycloak | [Real OAuth 2.0 Tokens From Keycloak](https://www.javanibble.com/rest-assured-guide/module-12/section-01.html) |
| 2. Running in Parallel | [Running REST Assured Tests in Parallel](https://www.javanibble.com/rest-assured-guide/module-12/section-02.html) |
| 3. Tags and Profiles | [Tags and Maven Profiles for REST Assured Suites](https://www.javanibble.com/rest-assured-guide/module-12/section-03.html) |

## The Domain: Protected Accounts and Reference Data

- [`ProtectedAccountsApi`](src/main/java/com/javanibble/resource/ProtectedAccountsApi.java): accounts (`accounts:read`) and payments (`payments:write`), protected by tokens from a real authorization server. [`JwksTokenVerifier`](src/main/java/com/javanibble/resource/JwksTokenVerifier.java) verifies RS256 signatures with the keys the server publishes as a JWKS, plus issuer, audience and expiry, and reloads the keys for an unknown key id.
- Keycloak 26.8 in Docker, with the realm [`open-banking-realm.json`](src/test/resources/keycloak/open-banking-realm.json): clients `tpp-accounts`/`accounts-secret` and `tpp-payments`/`payments-secret` with the client credentials grant, client scopes `accounts:read` and `payments:write`, and an audience mapper for `accounts-api`.
- [`ReferenceDataApi`](src/main/java/com/javanibble/reference/ReferenceDataApi.java): currencies and bank holidays, with a 200 ms delay per request so parallel execution shows.

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module (Docker running) | `mvn test -pl module-12` | 16 tests pass |
| Run the module without Docker | `mvn test -pl module-12` | 11 tests pass; the 5 Keycloak tests are skipped |
| Leave Docker tests out explicitly | `mvn test -pl module-12 -Pno-docker` | 11 tests pass |
| Smoke tests only | `mvn test -pl module-12 -Psmoke` | 2 tests pass |
| Security tests only | `mvn test -pl module-12 -Psecurity` | the 5 Keycloak tests |

The first run downloads the Keycloak image, which takes a while.
