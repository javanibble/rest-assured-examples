# REST Assured Examples

[![Java 26](https://img.shields.io/badge/Java-26-blue)](https://openjdk.org/projects/jdk/26/)
[![REST Assured 6.3.0](https://img.shields.io/badge/REST%20Assured-6.3.0-6DB33F)](https://rest-assured.io/)
[![JUnit 6.1.3](https://img.shields.io/badge/JUnit-6.1.3-25A162)](https://docs.junit.org/6.1.3/)
[![Maven 3.9](https://img.shields.io/badge/Maven-3.9-C71A36)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

Learn to test REST APIs in Java by reading, running and breaking real tests. In Java, the standard library for this is REST Assured: you describe an HTTP request, send it, and check the response in one readable `given().when().then()` chain, inside ordinary JUnit tests. This repository teaches REST Assured 6 by example, and continues the [JUnit 6 course](https://github.com/andre-mare/java-unit-testing). Every concept is a test you can run, read and change.

The course has twelve Maven modules, from a first request in Module 1 to request signing, WireMock, Spring Boot and real OAuth 2.0 tokens from Keycloak in Modules 8 to 12. Each module tests its own small HTTP API from banking and identity, such as accounts, payments, consents and an OAuth 2.0 resource server, which the tests start themselves, and the tests for each section are in their own package. The modules share no code, so you can work through them in order or start with the one you need.

## Table of Contents
- [REST Assured Examples](#rest-assured-examples)
  - [Table of Contents](#table-of-contents)
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

## Getting Started

### Prerequisites

- **JDK 26 or later.** The build compiles with `--release 26`, so an older JDK fails even though REST Assured 6 itself only needs Java 17.
- **Maven 3.9 or later.** The project has no Maven Wrapper, so Maven must be installed. It was tested with Maven 3.9.16.
- **Docker**, for the Keycloak tests in Module 12 only. Without Docker those tests are skipped, not failed.

### Build and Test

```bash
git clone https://github.com/andre-mare/java-rest-assured-testing.git
cd java-rest-assured-testing
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
| [Awaitility](https://www.awaitility.org/) | 4.3.0 | Waiting for asynchronous work | Module 9 |
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
- [Java Unit Testing with JUnit 6](https://github.com/andre-mare/java-unit-testing), the course this one builds on

## License

Released under the [MIT License](LICENSE).