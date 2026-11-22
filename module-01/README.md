# Module 1: Foundations

This module covers how a REST Assured test is put together, where its requests go, how to check a response, how to log requests and responses, and how to read what happens when a check fails.

## Table of Contents

- [Module 1: Foundations](#module-1-foundations)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Accounts](#the-domain-accounts)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-1-foundations). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Your First Request | [Send Your First REST Assured Request](https://www.javanibble.com/rest-assured-guide/module1-section1/) |
| 2. The Anatomy of a Test | [Take Apart a REST Assured Test](https://www.javanibble.com/rest-assured-guide/module1-section2/) |
| 3. Where Requests Go | [Control Where REST Assured Requests Go](https://www.javanibble.com/rest-assured-guide/module1-section3/) |
| 4. First Assertions | [Check Every Part of an HTTP Response](https://www.javanibble.com/rest-assured-guide/module1-section4/) |
| 5. Logging | [Log REST Assured Requests and Responses](https://www.javanibble.com/rest-assured-guide/module1-section5/) |
| 6. Reading Failures | [Read REST Assured Failure Messages](https://www.javanibble.com/rest-assured-guide/module1-section6/) |

## The Domain: Accounts

[`AccountsApi`](src/main/java/com/javanibble/accounts/AccountsApi.java) is a small, read-only accounts API served by the JDK's built-in HTTP server, so there is nothing to install. The tests start it on a free port.

| Request | Response |
| --- | --- |
| `GET /api/health` | 200, `{"status":"UP"}` |
| `GET /api/v1/accounts` | 200, the accounts; the optional query parameters `status` and `currency` filter the list, and an unknown status gives 400 |
| `GET /api/v1/accounts/{id}` | 200, one account, or 404 for an unknown id |
| Any other method on these paths | 405 with an `Allow: GET` header |

Every response has `Cache-Control: no-store` and a fresh `X-Request-Id`, and a request's `X-Correlation-Id` is echoed back. Errors are RFC 9457 problem details (`application/problem+json`).

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-01` | 36 tests pass |
| Run the deliberately failing tests | `mvn test -pl module-01 -DexcludedGroups=none -Dtest=DeliberatelyFailingTest` | 5 tests fail on purpose, and Maven reports `BUILD FAILURE` |
