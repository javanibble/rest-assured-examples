# Module 8: Filters and Extensibility

This module covers changing every request and response in one place with filters, signing requests, masking personal data in logs, a custom authentication scheme, parsers for misbehaving APIs, and listeners that react to failures.

## Table of Contents

- [Module 8: Filters and Extensibility](#module-8-filters-and-extensibility)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Signed Payments](#the-domain-signed-payments)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-8-filters-and-extensibility). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Writing Filters | [Write Request and Response Filters in REST Assured](https://www.javanibble.com/rest-assured-guide/module8-section1/) |
| 2. Useful Filters | [Write Timing, Masking and Repair Filters in REST Assured](https://www.javanibble.com/rest-assured-guide/module8-section2/) |
| 3. Signing Requests | [Sign Requests With HTTP Message Signatures in REST Assured](https://www.javanibble.com/rest-assured-guide/module8-section3/) |
| 4. A Custom Authentication Scheme | [Write a Custom Authentication Scheme in REST Assured](https://www.javanibble.com/rest-assured-guide/module8-section4/) |
| 5. Parsers and Failure Listeners | [Use Parsers and Failure Listeners in REST Assured](https://www.javanibble.com/rest-assured-guide/module8-section5/) |

## The Domain: Signed Payments

[`SignedPaymentsApi`](src/main/java/com/javanibble/signing/SignedPaymentsApi.java) requires requests to be signed, in the spirit of the request signing that FAPI and several open banking standards require. [`HttpSignatures`](src/main/java/com/javanibble/signing/HttpSignatures.java) describes the scheme: HTTP Message Signatures (RFC 9421, `hmac-sha256`) covering `@method`, `@path`, `content-digest` and `x-correlation-id`, plus `Content-Digest` (RFC 9530).

| Request | Rules |
| --- | --- |
| `POST /api/v1/payments` | `X-Correlation-Id`; signed; a correct `Content-Digest`; signatures older than five minutes are rejected |
| `GET /api/v1/payments/{id}` | `X-Correlation-Id`; signed |
| `GET /api/v1/partner/accounts` | `X-API-Key: partner-key-123` |
| `GET /api/v1/legacy/fx-rates` | None; sends JSON as `text/plain` and echoes no correlation id |

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-08` | 26 tests pass |
