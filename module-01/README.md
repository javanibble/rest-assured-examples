# Module 1: Foundations

This module covers how a REST Assured test is put together, where its requests go, how to check a response, how to log requests and responses, and how to read what happens when a check fails.

## Table of Contents

- [Module 1: Foundations](#module-1-foundations)
  - [Table of Contents](#table-of-contents)
  - [The Domain: Accounts](#the-domain-accounts)
  - [Running the Module](#running-the-module)
  - [Guides](#guides)
  - [Sections](#sections)
    - [Section 1: Your First Request](#section-1-your-first-request)
    - [Section 2: The Anatomy of a Test](#section-2-the-anatomy-of-a-test)
    - [Section 3: Where Requests Go](#section-3-where-requests-go)
    - [Section 4: First Assertions](#section-4-first-assertions)
    - [Section 5: Logging](#section-5-logging)
    - [Section 6: Reading Failures](#section-6-reading-failures)

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

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-01/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Your First Request | [Your First REST Assured Request](https://www.javanibble.com/rest-assured-guide/module-01/section-01.html) |
| 2. The Anatomy of a Test | [The Anatomy of a REST Assured Test](https://www.javanibble.com/rest-assured-guide/module-01/section-02.html) |
| 3. Where Requests Go | [Where REST Assured Requests Go](https://www.javanibble.com/rest-assured-guide/module-01/section-03.html) |
| 4. First Assertions | [First REST Assured Assertions](https://www.javanibble.com/rest-assured-guide/module-01/section-04.html) |
| 5. Logging | [Logging in REST Assured](https://www.javanibble.com/rest-assured-guide/module-01/section-05.html) |
| 6. Reading Failures | [Reading REST Assured Failures](https://www.javanibble.com/rest-assured-guide/module-01/section-06.html) |

## Sections

### Section 1: Your First Request

**Guide:** [Your First REST Assured Request](https://www.javanibble.com/rest-assured-guide/module-01/section-01.html)

| Class | What it tests |
| --- | --- |
| [`FirstRequestTest`](src/test/java/com/javanibble/module_01/section_01/FirstRequestTest.java) | Starting the API in `@BeforeAll`, a first `given().when().then()`, the status code and a first body check |

### Section 2: The Anatomy of a Test

**Guide:** [The Anatomy of a REST Assured Test](https://www.javanibble.com/rest-assured-guide/module-01/section-02.html)

| Class | What it tests |
| --- | --- |
| [`GivenWhenThenTest`](src/test/java/com/javanibble/module_01/section_02/GivenWhenThenTest.java) | The full form, leaving out `given()`, the shortest form, the `RequestSpecification` as an ordinary object, keeping a `Response` to check later, and a first path parameter |

### Section 3: Where Requests Go

**Guide:** [Where REST Assured Requests Go](https://www.javanibble.com/rest-assured-guide/module-01/section-03.html)

| Class | What it tests |
| --- | --- |
| [`PerRequestConfigurationTest`](src/test/java/com/javanibble/module_01/section_03/PerRequestConfigurationTest.java) | `basePath` in front of every path, the port as part of the base URI, and an absolute URL that wins over both |
| [`StaticConfigurationTest`](src/test/java/com/javanibble/module_01/section_03/StaticConfigurationTest.java) | Static defaults on `RestAssured`, a value on the request that overrides them, and `RestAssured.reset()` |

### Section 4: First Assertions

**Guide:** [First REST Assured Assertions](https://www.javanibble.com/rest-assured-guide/module-01/section-04.html)

| Class | What it tests |
| --- | --- |
| [`FirstAssertionsTest`](src/test/java/com/javanibble/module_01/section_04/FirstAssertionsTest.java) | Status code and status line, content type, headers and their case-insensitive names, single values, several paths in one `body(..)` call, values inside a list, numbers as `Float`, query parameters, problem details, and response time |

### Section 5: Logging

**Guide:** [Logging in REST Assured](https://www.javanibble.com/rest-assured-guide/module-01/section-05.html)

| Class | What it tests |
| --- | --- |
| [`LoggingTest`](src/test/java/com/javanibble/module_01/section_05/LoggingTest.java) | `log().all()`, logging only parts, `log().ifValidationFails()`, `log().ifError()` and `log().ifStatusCodeIsEqualTo(..)` |

### Section 6: Reading Failures

**Guide:** [Reading REST Assured Failures](https://www.javanibble.com/rest-assured-guide/module-01/section-06.html)

| Class | What it tests |
| --- | --- |
| [`FailureMessagesTest`](src/test/java/com/javanibble/module_01/section_06/FailureMessagesTest.java) | The failure messages for a wrong status code and a wrong body value, a `then()` chain that stops at its first failure, and all paths of one `body(..)` call reported together |
| [`DeliberatelyFailingTest`](src/test/java/com/javanibble/module_01/section_06/DeliberatelyFailingTest.java) | Five tests that fail on purpose, so you can read the messages; tagged `expected-failure-tests` and excluded from the default build |
