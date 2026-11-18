# Module 11: Spring Boot Integration

This module covers the two ways to test a Spring Boot API with REST Assured, and when to use each: `RestAssuredMockMvc` without a server, with or without a Spring context, and plain REST Assured against a real server on a random port. It also covers keeping library versions aligned with Spring Boot's.

## Table of Contents

- [Module 11: Spring Boot Integration](#module-11-spring-boot-integration)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: A Spring Boot Accounts Service](#the-domain-a-spring-boot-accounts-service)
  - [Running the Module](#running-the-module)
  - [Sections](#sections)
    - [Section 1: RestAssuredMockMvc in Standalone Mode](#section-1-restassuredmockmvc-in-standalone-mode)
    - [Section 2: A Web Slice With Spring Security](#section-2-a-web-slice-with-spring-security)
    - [Section 3: A Real Server on a Random Port](#section-3-a-real-server-on-a-random-port)
    - [Section 4: Keeping Versions Aligned](#section-4-keeping-versions-aligned)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-11/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. RestAssuredMockMvc in Standalone Mode | [RestAssuredMockMvc in Standalone Mode](https://www.javanibble.com/rest-assured-guide/module-11/section-01.html) |
| 2. A Web Slice With Spring Security | [A Web Slice With Spring Security](https://www.javanibble.com/rest-assured-guide/module-11/section-02.html) |
| 3. A Real Server on a Random Port | [Testing Spring Boot on a Random Port With REST Assured](https://www.javanibble.com/rest-assured-guide/module-11/section-03.html) |
| 4. Keeping Versions Aligned | [Keeping Versions Aligned](https://www.javanibble.com/rest-assured-guide/module-11/section-04.html) |

## The Domain: A Spring Boot Accounts Service

[`AccountsApplication`](src/main/java/com/javanibble/AccountsApplication.java) is a Spring Boot service. [`AccountController`](src/main/java/com/javanibble/accounts/AccountController.java) serves `GET /api/v1/accounts`, `GET /api/v1/accounts/{id}` and `POST /api/v1/accounts`.

[`SecurityConfig`](src/main/java/com/javanibble/accounts/SecurityConfig.java) makes the service an OAuth 2.0 resource server:

- reading accounts needs the scope `accounts:read`, and opening one needs `accounts:write`
- tokens are JWTs signed with RS256, verified with the public key in [`jwt/trusted-public-key.pem`](src/main/resources/jwt/trusted-public-key.pem), and must have the configured audience
- without a token the answer is 401 with `WWW-Authenticate: Bearer`, and without the scope 403 with `error="insufficient_scope"`

[`TestTokens`](src/test/java/com/javanibble/module_11/support/TestTokens.java) signs tokens for the random-port tests with the matching private key from [`src/test/resources/jwt`](src/test/resources/jwt).

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-11` | 16 tests pass |

## Sections

### Section 1: RestAssuredMockMvc in Standalone Mode

**Guide:** [RestAssuredMockMvc in Standalone Mode](https://www.javanibble.com/rest-assured-guide/module-11/section-01.html)

| Class | What it tests |
| --- | --- |
| [`StandaloneMockMvcTest`](src/test/java/com/javanibble/module_11/section_01/StandaloneMockMvcTest.java) | Listing, reading and opening accounts, an unknown account and an invalid account, with `standaloneSetup(..)` and no Spring context |

### Section 2: A Web Slice With Spring Security

**Guide:** [A Web Slice With Spring Security](https://www.javanibble.com/rest-assured-guide/module-11/section-02.html)

| Class | What it tests |
| --- | --- |
| [`WebMvcSliceTest`](src/test/java/com/javanibble/module_11/section_02/WebMvcSliceTest.java) | `@WebMvcTest` with the real security configuration: a reader, a request without a token, a reader who may not open accounts, a writer who may, and errors as problem details, with Spring Security's `jwt()` through `postProcessors(..)` |

### Section 3: A Real Server on a Random Port

**Guide:** [Testing Spring Boot on a Random Port With REST Assured](https://www.javanibble.com/rest-assured-guide/module-11/section-03.html)

| Class | What it tests |
| --- | --- |
| [`RandomPortServerTest`](src/test/java/com/javanibble/module_11/section_03/RandomPortServerTest.java) | `@SpringBootTest(webEnvironment = RANDOM_PORT)` with real signed tokens: a reader, a writer who opens an account that a reader then reads, an expired token, a token for another audience, the wrong scope, and an unknown account |

### Section 4: Keeping Versions Aligned

**Guide:** [Keeping Versions Aligned](https://www.javanibble.com/rest-assured-guide/module-11/section-04.html)

| Class | What it tests |
| --- | --- |
| [`pom.xml`](pom.xml) | No test classes: the `dependencyManagement` imports `junit-bom`, `rest-assured-bom` and `jackson-bom` before `spring-boot-dependencies`, because the first imported BOM wins and Spring Boot doesn't manage REST Assured |
