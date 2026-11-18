# Module 9: Data-Driven Tests and API Workflows

This module covers testing many inputs without copying tests, and testing flows that span several requests: parameterized and dynamic tests, payment workflows, idempotency keys, ETags, pagination and asynchronous APIs.

## Table of Contents

- [Module 9: Data-Driven Tests and API Workflows](#module-9-data-driven-tests-and-api-workflows)
  - [Table of Contents](#table-of-contents)
  - [The Domain: The Payment Lifecycle](#the-domain-the-payment-lifecycle)
  - [Running the Module](#running-the-module)
  - [Guides](#guides)
  - [Sections](#sections)
    - [Section 1: Parameterized API Tests](#section-1-parameterized-api-tests)
    - [Section 2: Dynamic API Tests](#section-2-dynamic-api-tests)
    - [Section 3: API Workflows](#section-3-api-workflows)
    - [Section 4: HTTP Semantics](#section-4-http-semantics)
    - [Section 5: Asynchronous APIs](#section-5-asynchronous-apis)

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

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-09/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Parameterized API Tests | [Parameterized API Tests With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-01.html) |
| 2. Dynamic API Tests | [Dynamic API Tests With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-02.html) |
| 3. API Workflows | [API Workflows With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-03.html) |
| 4. HTTP Semantics | [HTTP Semantics in REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-04.html) |
| 5. Asynchronous APIs | [Testing Asynchronous APIs With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-05.html) |

## Sections

### Section 1: Parameterized API Tests

**Guide:** [Parameterized API Tests With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-01.html)

| Class | What it tests |
| --- | --- |
| [`ValidationMatrixTest`](src/test/java/com/javanibble/module_09/section_01/ValidationMatrixTest.java) | Invalid fields with `@CsvSource`, boundary amounts with `@ValueSource`, IBANs from several countries with `@FieldSource`, and several problems at once with `@MethodSource` |
| [`PerCurrencyTest`](src/test/java/com/javanibble/module_09/section_01/PerCurrencyTest.java) | A whole class repeated per currency with `@ParameterizedClass` |

### Section 2: Dynamic API Tests

**Guide:** [Dynamic API Tests With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-02.html)

| Class | What it tests |
| --- | --- |
| [`DynamicTestsTest`](src/test/java/com/javanibble/module_09/section_02/DynamicTestsTest.java) | A `@TestFactory` that checks every endpoint rejects a missing or wrong token, grouped per endpoint with `dynamicContainer` |

### Section 3: API Workflows

**Guide:** [API Workflows With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-03.html)

| Class | What it tests |
| --- | --- |
| [`PaymentWorkflowTest`](src/test/java/com/javanibble/module_09/section_03/PaymentWorkflowTest.java) | Initiate, authorise, execute and settle a payment in order with `@Nested` and `@Order`; insufficient funds; authorising only once; and authorising before execution |

### Section 4: HTTP Semantics

**Guide:** [HTTP Semantics in REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-04.html)

| Class | What it tests |
| --- | --- |
| [`HttpSemanticsTest`](src/test/java/com/javanibble/module_09/section_04/HttpSemanticsTest.java) | Idempotency keys (the same request twice, a reused key, a missing key), ETags with `If-None-Match` and `If-Match` and a stale ETag, and cursor pagination with `Link` headers |

### Section 5: Asynchronous APIs

**Guide:** [Testing Asynchronous APIs With REST Assured](https://www.javanibble.com/rest-assured-guide/module-09/section-05.html)

| Class | What it tests |
| --- | --- |
| [`AsyncPollingTest`](src/test/java/com/javanibble/module_09/section_05/AsyncPollingTest.java) | Polling the `Location` with Awaitility until the work is done, respecting `Retry-After`, and waiting for a final state before checking which one |
