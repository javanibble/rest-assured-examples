# Module 9: Data-Driven Tests and API Workflows

This module covers testing many inputs without copying tests, and testing flows that span several requests: parameterized and dynamic tests, payment workflows, idempotency keys, ETags, pagination and asynchronous APIs.

## Table of Contents

- [Module 9: Data-Driven Tests and API Workflows](#module-9-data-driven-tests-and-api-workflows)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: The Payment Lifecycle](#the-domain-the-payment-lifecycle)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-9-data-driven-tests-and-api-workflows). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Parameterized API Tests | [Write Parameterized API Tests With REST Assured](https://www.javanibble.com/rest-assured-guide/module9-section1/) |
| 2. Dynamic API Tests | [Generate Dynamic API Tests With REST Assured](https://www.javanibble.com/rest-assured-guide/module9-section2/) |
| 3. API Workflows | [Test API Workflows With REST Assured](https://www.javanibble.com/rest-assured-guide/module9-section3/) |
| 4. HTTP Semantics | [Test Idempotency, ETags and Pagination in REST Assured](https://www.javanibble.com/rest-assured-guide/module9-section4/) |
| 5. Asynchronous APIs | [Test Asynchronous APIs With REST Assured](https://www.javanibble.com/rest-assured-guide/module9-section5/) |

## The Domain: The Payment Lifecycle

[`PaymentLifecycleApi`](src/main/java/com/javanibble/lifecycle/PaymentLifecycleApi.java) covers the full life of a payment, with the HTTP semantics that real payment APIs need. All endpoints are under `/api/v1`, and every request needs `Authorization: Bearer test-token`.

| Request | Response |
| --- | --- |
| `POST /payments` with `Idempotency-Key` | 201 `INITIATED`; the same key and body again gives the same payment with `Idempotent-Replayed: true`; the same key with another body gives 422. Field errors give 422 with `errors` |
| `GET /payments/{id}` | 200 with an `ETag`; 304 for a matching `If-None-Match` |
| `PATCH /payments/{id}` | Needs `If-Match`: 428 without it, 412 when stale, 409 once authorised |
| `POST /payments/{id}/authorisation` | 200 `AUTHORISED`, or 409 |
| `POST /payments/{id}/execution` | 202 `EXECUTING` with `Location` and `Retry-After`; then `EXECUTED`, or `REJECTED` with `INSUFFICIENT_FUNDS` for amounts above 5000.00 |
| `GET /payments?limit=..&cursor=..` | 200 `items`, oldest first, with a `Link: <..>; rel="next"` header while there are more |

[`PaymentsClient`](src/test/java/com/javanibble/module_09/support/PaymentsClient.java) holds the specifications and valid payment bodies the tests share.

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-09` | 67 tests pass |
