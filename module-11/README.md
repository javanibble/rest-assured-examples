# Module 11: Spring Boot Integration

This module covers the two ways to test a Spring Boot API with REST Assured, and when to use each: `RestAssuredMockMvc` without a server, with or without a Spring context, and plain REST Assured against a real server on a random port. It also covers keeping library versions aligned with Spring Boot's.

## Table of Contents

- [Module 11: Spring Boot Integration](#module-11-spring-boot-integration)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: A Spring Boot Accounts Service](#the-domain-a-spring-boot-accounts-service)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-11-spring-boot-integration). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. RestAssuredMockMvc in Standalone Mode | [Test a Spring Controller With RestAssuredMockMvc in Standalone Mode](https://www.javanibble.com/rest-assured-guide/module11-section1/) |
| 2. A Web Slice With Spring Security | [Test Spring Security Rules in a Web Slice With RestAssuredMockMvc](https://www.javanibble.com/rest-assured-guide/module11-section2/) |
| 3. A Real Server on a Random Port | [Test Spring Boot on a Random Port With REST Assured and Real Tokens](https://www.javanibble.com/rest-assured-guide/module11-section3/) |
| 4. Keeping Versions Aligned (no test class — see [`pom.xml`](pom.xml#L17)) | [Keep Versions Aligned Under Spring Boot's Dependency Management](https://www.javanibble.com/rest-assured-guide/module11-section4/) |

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
