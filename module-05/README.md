# Module 5: Reusable Specifications and Configuration

This module covers removing repetition from a test suite and configuring REST Assured safely, without static state: request and response specifications, `RestAssuredConfig`, keeping secrets out of logs, and a JUnit extension that injects ready specifications.

## Table of Contents

- [Module 5: Reusable Specifications and Configuration](#module-5-reusable-specifications-and-configuration)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Open Banking Accounts](#the-domain-open-banking-accounts)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-05/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Request Specifications | [Request Specifications in REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-01.html) |
| 2. Response Specifications | [Response Specifications in REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-02.html) |
| 3. Client Configuration | [Client Configuration in REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-03.html) |
| 4. Keeping Secrets Out of Logs | [Keeping Secrets Out of REST Assured Logs](https://www.javanibble.com/rest-assured-guide/module-05/section-04.html) |
| 5. A JUnit Extension | [A JUnit Extension for REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-05.html) |

## The Domain: Open Banking Accounts

[`OpenBankingApi`](src/main/java/com/javanibble/openbanking/OpenBankingApi.java) is an account information API in the style of the UK Open Banking standard, under `/open-banking/v4.0/aisp`: `GET /accounts`, `GET /accounts/{id}` and `GET /accounts/{id}/balances`.

Every request must have:

- `Authorization: Bearer <token>` with a valid token, otherwise 401 with `WWW-Authenticate: Bearer`
- an `x-fapi-interaction-id` header, otherwise 400; it is echoed in the response
- an `Accept` header that allows JSON, otherwise 406

Under `/test-support`, with none of these rules, are endpoints for client configuration: `GET /slow?millis=..`, `GET` and `POST /redirect`, `/landing`, and `GET /greeting`, which sends UTF-8 text without declaring a charset.

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-05` | 24 tests pass |
