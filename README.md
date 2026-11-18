# REST Assured Examples

[![Java 26](https://img.shields.io/badge/Java-26-blue)](https://openjdk.org/projects/jdk/26/)
[![REST Assured 6.3.0](https://img.shields.io/badge/REST%20Assured-6.3.0-6DB33F)](https://rest-assured.io/)
[![JUnit 6.1.3](https://img.shields.io/badge/JUnit-6.1.3-25A162)](https://docs.junit.org/6.1.3/)
[![Maven 3.9](https://img.shields.io/badge/Maven-3.9-C71A36)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

REST Assured Examples is a practical, hands-on repository showcasing clean patterns and real-world code examples for automated API testing in Java. It demonstrates how to leverage REST Assured’s fluent API alongside modern JUnit to validate HTTP responses, headers, and payloads cleanly. 

Inside, you'll find working implementations covering JSON serialisation, reusable request specifications, schema validation, and authentication flows. Whether you are setting up your first API test suite or standardising integration tests across a microservice architecture, this repo serves as a straightforward reference.

## Table of Contents
- [REST Assured Examples](#rest-assured-examples)
  - [Table of Contents](#table-of-contents)
  - [Guide](#guide)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Build and Test](#build-and-test)
  - [Running Tests](#running-tests)
    - [Tags and Module 12 Profiles](#tags-and-module-12-profiles)
  - [Deliberately Failing Tests](#deliberately-failing-tests)
  - [Parallel Execution](#parallel-execution)
  - [Technology Stack](#technology-stack)
  - [Resources](#resources)
  - [License](#license)

## Guide

The [REST Assured Guide](https://www.javanibble.com/rest-assured-guide/) on Java Nibble is the documentation for this repository. It has a page for every module and a step-by-step guide for every section, which explains the concepts behind the tests, walks through the code and points out common mistakes. Each module's README links to the guide for each of its sections.

| Module | Level | What you will learn | Documentation |
| --- | --- | --- | --- |
| [1. Foundations](module-01/) | Beginner | given/when/then, where requests go, first assertions, logging, reading failures | [Module 1 Guide](https://www.javanibble.com/rest-assured-guide/module-01/) |
| [2. Building Requests](module-02/) | Beginner | Path, query and form parameters, headers, cookies, bodies, every HTTP method, multipart uploads | [Module 2 Guide](https://www.javanibble.com/rest-assured-guide/module-02/) |
| [3. Validating Responses in Depth](module-03/) | Beginner to Intermediate | GPath for JSON and XML, Hamcrest matchers, root paths, numbers and money, cookies, response-aware matchers | [Module 3 Guide](https://www.javanibble.com/rest-assured-guide/module-03/) |
| [4. Extracting Values and Mapping Objects](module-04/) | Intermediate | `extract()`, `JsonPath`, mapping to records, generics with `TypeRef`, Jackson 3, chaining requests | [Module 4 Guide](https://www.javanibble.com/rest-assured-guide/module-04/) |
| [5. Reusable Specifications and Configuration](module-05/) | Intermediate | Request and response specifications, `RestAssuredConfig`, timeouts, redirects, hiding secrets in logs, a JUnit extension | [Module 5 Guide](https://www.javanibble.com/rest-assured-guide/module-05/) |
| [6. Schema and Contract Validation](module-06/) | Intermediate | JSON Schema (draft 2020-12), XSD, RFC 9457 errors, OpenAPI validation | [Module 6 Guide](https://www.javanibble.com/rest-assured-guide/module-06/) |
| [7. Authentication and Transport Security](module-07/) | Intermediate to Advanced | Basic, bearer tokens, the client credentials grant, negative security tests, sessions and CSRF, TLS, mutual TLS | [Module 7 Guide](https://www.javanibble.com/rest-assured-guide/module-07/) |
| [8. Filters and Extensibility](module-08/) | Advanced | Custom filters, request signing, masking logs, custom authentication schemes, parsers and failure listeners | [Module 8 Guide](https://www.javanibble.com/rest-assured-guide/module-08/) |
| [9. Data-Driven Tests and API Workflows](module-09/) | Advanced | Parameterized API tests, dynamic tests, multi-step workflows, idempotency, ETags, pagination, polling | [Module 9 Guide](https://www.javanibble.com/rest-assured-guide/module-09/) |
| [10. Stubbing Dependencies with WireMock](module-10/) | Advanced | Stubbing a third-party API, fault injection, verifying outgoing calls, templates and scenarios | [Module 10 Guide](https://www.javanibble.com/rest-assured-guide/module-10/) |
| [11. Spring Boot Integration](module-11/) | Advanced | `RestAssuredMockMvc`, Spring Security test support, full server tests on a random port | [Module 11 Guide](https://www.javanibble.com/rest-assured-guide/module-11/) |
| [12. Real Infrastructure with Testcontainers](module-12/) | Advanced | Real tokens from Keycloak, parallel execution, tags and Maven profiles | [Module 12 Guide](https://www.javanibble.com/rest-assured-guide/module-12/) |

## Getting Started

### Prerequisites

- **JDK 26 or later.** The build compiles with `--release 26`, so an older JDK fails even though REST Assured 6 itself only needs Java 17.
- **Maven 3.9 or later.** The project has no Maven Wrapper, so Maven must be installed. It was tested with Maven 3.9.16.
- **Docker**, for the Keycloak tests in Module 12 only. Without Docker those tests are skipped, not failed.

### Build and Test

```bash
git clone https://github.com/javanibble/rest-assured-examples.git
cd rest-assured-examples
mvn test
```

The build should end with `BUILD SUCCESS`. The tests start their own APIs on free ports of `localhost`, so nothing else needs to be running. On a machine without Docker, the Keycloak tests in Module 12 are reported as skipped.

## Running Tests

Run these commands from the repository root.

| Goal | Command |
| --- | --- |
| Run everything | `mvn test` |
| Run one module | `mvn test -pl module-07` |
| Run one class | `mvn test -pl module-03 -Dtest=NumbersAndMoneyTest` |
| Run one test method | `mvn test -pl module-03 -Dtest=NumbersAndMoneyTest#decimalsAsBigDecimal` |

### Tags and Module 12 Profiles

Module 12 defines Maven profiles for its tags:

```bash
mvn test -pl module-12 -Psmoke        # only smoke tests
mvn test -pl module-12 -Psecurity     # only the authentication and authorization checks
mvn test -pl module-12 -Pno-docker    # everything except the Keycloak tests
```

## Deliberately Failing Tests

Some classes fail on purpose so you can read REST Assured's failure messages. They carry the `expected-failure-tests` tag, which the parent POM excludes by default. To run one, clear the filter and name the class:

```bash
mvn test -pl module-01 -DexcludedGroups=none -Dtest=DeliberatelyFailingTest
```

Maven reports `BUILD FAILURE` for these runs. That is the expected result. Other modules show failures inside passing tests with `assertThrows`.

| Module | Classes |
| --- | --- |
| 1 | `DeliberatelyFailingTest` |

## Parallel Execution

Tests run sequentially by default. Module 12 enables parallel execution in its [`junit-platform.properties`](module-12/src/test/resources/junit-platform.properties), but only for classes that opt in with `@Execution(CONCURRENT)`, on a fixed pool of four threads. `ParallelExecutionTest` is the class that opts in. Compare the time Surefire reports for it with and without the annotation, and read its class comment for the rules that make REST Assured tests safe to run in parallel.

```bash
mvn test -pl module-12 -Dtest=ParallelExecutionTest
```

## Technology Stack

| Tool | Version | Used for | Scope |
| --- | --- | --- | --- |
| [REST Assured](https://rest-assured.io/) | 6.3.0, through `rest-assured-bom` | Sending requests and checking responses | All modules |
| [REST Assured JSON Schema Validator](https://github.com/rest-assured/rest-assured/wiki/Usage#json-schema-validation) | 6.3.0, through `rest-assured-bom` | Validating responses against JSON Schema | Module 6 |
| [REST Assured Spring Mock MVC](https://github.com/rest-assured/rest-assured/wiki/Spring) | 6.3.0, through `rest-assured-bom` | Testing Spring MVC controllers without a server | Module 11 |
| [JUnit Jupiter](https://junit.org/) | 6.1.3, through `junit-bom` | Test framework | All modules |
| [Hamcrest](https://hamcrest.org/JavaHamcrest/) | from REST Assured | Matchers | All modules |
| [Jackson](https://github.com/FasterXML/jackson) | 3.2.3, through `jackson-bom` | JSON in the APIs and object mapping in the tests | All modules |
| [AssertJ](https://assertj.github.io/doc/) | 3.27.7 | Fluent assertions on mapped objects | Modules 4, 9 |
| [Awaitility](https://github.com/awaitility/awaitility) | 4.3.0 | Waiting for asynchronous work | Module 9 |
| [WireMock](https://wiremock.org/) | 3.13.2 | Stubbing a third-party API | Module 10 |
| [Spring Boot](https://spring.io/projects/spring-boot) | 4.1.2 | The application under test | Module 11 |
| [Testcontainers](https://testcontainers.com/) | 2.0.5, through `testcontainers-bom` | Running containers from tests | Module 12 |
| [testcontainers-keycloak](https://github.com/dasniko/testcontainers-keycloak) | 4.4.0 | Keycloak 26.8 in Docker | Module 12 |
| [Maven Compiler Plugin](https://maven.apache.org/plugins/maven-compiler-plugin/) | 3.15.0 | Compiles with `-parameters` for readable parameterized test names | All modules |
| [Maven Surefire Plugin](https://maven.apache.org/surefire/maven-surefire-plugin/) | 3.5.5 | Runs the tests | All modules |

## Resources

- [REST Assured Usage Guide](https://github.com/rest-assured/rest-assured/wiki/Usage)
- [REST Assured Release Notes](https://github.com/rest-assured/rest-assured/wiki/ReleaseNotes)
- [REST Assured Javadoc](https://javadoc.io/doc/io.rest-assured/rest-assured/latest/index.html)
- [JUnit 6.1.3 User Guide](https://docs.junit.org/6.1.3/)
- [Java Unit Testing with JUnit 6](https://github.com/andre-mare/java-unit-testing)

## License

Released under the [MIT License](LICENSE).