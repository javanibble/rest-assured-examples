# Module 5: Reusable Specifications and Configuration

This module covers removing repetition from a test suite and configuring REST Assured safely, without static state: request and response specifications, `RestAssuredConfig`, keeping secrets out of logs, and a JUnit extension that injects ready specifications.

## Table of Contents

- [Module 5: Reusable Specifications and Configuration](#module-5-reusable-specifications-and-configuration)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Open Banking Accounts](#the-domain-open-banking-accounts)
  - [Running the Module](#running-the-module)
  - [Sections](#sections)
    - [Section 1: Request Specifications](#section-1-request-specifications)
    - [Section 2: Response Specifications](#section-2-response-specifications)
    - [Section 3: Client Configuration](#section-3-client-configuration)
    - [Section 4: Keeping Secrets Out of Logs](#section-4-keeping-secrets-out-of-logs)
    - [Section 5: A JUnit Extension](#section-5-a-junit-extension)

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

## Sections

### Section 1: Request Specifications

**Guide:** [Request Specifications in REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-01.html)

| Class | What it tests |
| --- | --- |
| [`RequestSpecificationTest`](src/test/java/com/javanibble/module_05/section_01/RequestSpecificationTest.java) | `given(spec)` and `spec(..)`, a specification built on another, overriding a header of a specification, values other than headers being replaced, and inspecting a specification with `SpecificationQuerier` |

### Section 2: Response Specifications

**Guide:** [Response Specifications in REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-02.html)

| Class | What it tests |
| --- | --- |
| [`ResponseSpecificationTest`](src/test/java/com/javanibble/module_05/section_02/ResponseSpecificationTest.java) | `then().spec(..)`, both specifications in `given(..)`, a shared shape for errors, and a specification that reports all its failures at once |

### Section 3: Client Configuration

**Guide:** [Client Configuration in REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-03.html)

| Class | What it tests |
| --- | --- |
| [`ClientConfigurationTest`](src/test/java/com/javanibble/module_05/section_03/ClientConfigurationTest.java) | Read timeouts, following redirects (and turning it off, and `followRedirectsForAllMethods`), and the default charset with `DecoderConfig` |

### Section 4: Keeping Secrets Out of Logs

**Guide:** [Keeping Secrets Out of REST Assured Logs](https://www.javanibble.com/rest-assured-guide/module-05/section-04.html)

| Class | What it tests |
| --- | --- |
| [`SecretsInLogsTest`](src/test/java/com/javanibble/module_05/section_04/SecretsInLogsTest.java) | Capturing the log in a stream, masking headers with `blacklistHeader(..)`, masking when logging on failure, and nothing logged when the test passes |

### Section 5: A JUnit Extension

**Guide:** [A JUnit Extension for REST Assured](https://www.javanibble.com/rest-assured-guide/module-05/section-05.html)

| Class | What it tests |
| --- | --- |
| [`InjectedSpecificationTest`](src/test/java/com/javanibble/module_05/section_05/InjectedSpecificationTest.java) | Tests that receive a ready `RequestSpecification` and `ResponseSpecification` as parameters, each with its own interaction id |
| [`OpenBankingApiExtension`](src/test/java/com/javanibble/module_05/section_05/support/OpenBankingApiExtension.java) | Starts the API once and injects the specifications with a `ParameterResolver` |
| [`OpenBankingApiTest`](src/test/java/com/javanibble/module_05/section_05/support/OpenBankingApiTest.java) | A composed annotation that registers the extension |
