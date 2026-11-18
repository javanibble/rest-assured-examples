# Module 8: Filters and Extensibility

This module covers changing every request and response in one place with filters, signing requests, masking personal data in logs, a custom authentication scheme, parsers for misbehaving APIs, and listeners that react to failures.

## Table of Contents

- [Module 8: Filters and Extensibility](#module-8-filters-and-extensibility)
  - [Table of Contents](#table-of-contents)
  - [The Domain: Signed Payments](#the-domain-signed-payments)
  - [Running the Module](#running-the-module)
  - [Guides](#guides)
  - [Sections](#sections)
    - [Section 1: Writing Filters](#section-1-writing-filters)
    - [Section 2: Useful Filters](#section-2-useful-filters)
    - [Section 3: Signing Requests](#section-3-signing-requests)
    - [Section 4: A Custom Authentication Scheme](#section-4-a-custom-authentication-scheme)
    - [Section 5: Parsers and Failure Listeners](#section-5-parsers-and-failure-listeners)

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

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-08/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Writing Filters | [Writing Filters in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-01.html) |
| 2. Useful Filters | [Useful Filters for REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-02.html) |
| 3. Signing Requests | [Signing Requests in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-03.html) |
| 4. A Custom Authentication Scheme | [A Custom Authentication Scheme in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-04.html) |
| 5. Parsers and Failure Listeners | [Parsers and Failure Listeners in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-05.html) |

## Sections

### Section 1: Writing Filters

**Guide:** [Writing Filters in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-01.html)

| Class | What it tests |
| --- | --- |
| [`WritingFiltersTest`](src/test/java/com/javanibble/module_08/section_01/WritingFiltersTest.java) | A filter that adds a header, a lambda filter, a filter that sees responses, a filter that fails a test, ordered filters, filters in a specification, and global filters |
| [`CorrelationIdFilter`](src/test/java/com/javanibble/module_08/support/CorrelationIdFilter.java) | Adds `X-Correlation-Id` and checks that the response echoes it |

### Section 2: Useful Filters

**Guide:** [Useful Filters for REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-02.html)

| Class | What it tests |
| --- | --- |
| [`UsefulFiltersTest`](src/test/java/com/javanibble/module_08/section_02/UsefulFiltersTest.java) | Timing, a log with IBANs and names masked, the built-in logging filters, and repairing a response with `ResponseBuilder` |
| [`MaskingLogFilter`](src/test/java/com/javanibble/module_08/support/MaskingLogFilter.java) | Logs each exchange with personal data masked |

### Section 3: Signing Requests

**Guide:** [Signing Requests in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-03.html)

| Class | What it tests |
| --- | --- |
| [`RequestSigningTest`](src/test/java/com/javanibble/module_08/section_03/RequestSigningTest.java) | Signed POST and GET accepted; unsigned, tampered-after-signing, wrongly signed and stale requests rejected |
| [`HttpMessageSignatureFilter`](src/test/java/com/javanibble/module_08/support/HttpMessageSignatureFilter.java) | Adds `Content-Digest`, `Signature-Input` and `Signature`; written from the RFCs, independently of the server's code |

### Section 4: A Custom Authentication Scheme

**Guide:** [A Custom Authentication Scheme in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-04.html)

| Class | What it tests |
| --- | --- |
| [`CustomAuthenticationTest`](src/test/java/com/javanibble/module_08/section_04/CustomAuthenticationTest.java) | An API key scheme in a specification, removed with `auth().none()`, replaced by another scheme, and as the global default |

### Section 5: Parsers and Failure Listeners

**Guide:** [Parsers and Failure Listeners in REST Assured](https://www.javanibble.com/rest-assured-guide/module-08/section-05.html)

| Class | What it tests |
| --- | --- |
| [`ParsersAndListenersTest`](src/test/java/com/javanibble/module_08/section_05/ParsersAndListenersTest.java) | A body that is just text, `parser("text/plain", Parser.JSON)` per response and in a specification, and a `ResponseValidationFailureListener` that collects evidence |
