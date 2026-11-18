# Module 10: Stubbing Dependencies with WireMock

This module covers testing an API whose own dependency you don't control, by replacing it with a WireMock stub: stubbing and request matching, faults and slowness, verifying outgoing calls, and response templates and stateful scenarios.

## Table of Contents

- [Module 10: Stubbing Dependencies with WireMock](#module-10-stubbing-dependencies-with-wiremock)
  - [Table of Contents](#table-of-contents)
  - [The Domain: A Quote Service and an FX Provider](#the-domain-a-quote-service-and-an-fx-provider)
  - [Running the Module](#running-the-module)
  - [Guides](#guides)
  - [Sections](#sections)
    - [Section 1: Stubbing a Dependency](#section-1-stubbing-a-dependency)
    - [Section 2: Provider Faults](#section-2-provider-faults)
    - [Section 3: Verifying Outgoing Calls](#section-3-verifying-outgoing-calls)
    - [Section 4: Response Templates and Scenarios](#section-4-response-templates-and-scenarios)

## The Domain: A Quote Service and an FX Provider

[`QuoteService`](src/main/java/com/javanibble/fx/QuoteService.java) converts an amount with the exchange rate of an external FX provider. In production the provider is a third party; in the tests WireMock plays it.

- **Our API:** `POST /api/v1/quotes` with `{"sourceCurrency": "EUR", "targetCurrency": "GBP", "sourceAmount": "100.00"}` answers 201 with the `rate` and the `targetAmount`, rounded half-even to two decimals. Invalid input is rejected with 422 before the provider is called.
- **The call to the provider:** `GET {provider}/v2/rates?base=EUR&symbols=GBP` with `Authorization: Bearer fx-api-key`, `X-Client-Id: payments-service` and `Accept: application/json`, expecting `{"base": "EUR", "rates": {"GBP": 0.8523}}`.

| When the provider answers | Our API answers |
| --- | --- |
| 503 | One retry; if it fails again, 502 |
| Another 5xx | 502 |
| 429 with `Retry-After` | 503 with the same `Retry-After` |
| 404 (unknown currency) | 422 |
| Nothing within one second | 504 |
| A connection failure, malformed JSON or no rate | 502 |

[`FxProviderStubs`](src/test/java/com/javanibble/module_10/support/FxProviderStubs.java) builds the provider's requests and responses for the stubs.

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-10` | 19 tests pass |

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-10/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Stubbing a Dependency | [Stubbing a Dependency With WireMock](https://www.javanibble.com/rest-assured-guide/module-10/section-01.html) |
| 2. Provider Faults | [Simulating Provider Faults With WireMock](https://www.javanibble.com/rest-assured-guide/module-10/section-02.html) |
| 3. Verifying Outgoing Calls | [Verifying Outgoing Calls With WireMock](https://www.javanibble.com/rest-assured-guide/module-10/section-03.html) |
| 4. Response Templates and Scenarios | [WireMock Response Templates and Scenarios](https://www.javanibble.com/rest-assured-guide/module-10/section-04.html) |

## Sections

### Section 1: Stubbing a Dependency

**Guide:** [Stubbing a Dependency With WireMock](https://www.javanibble.com/rest-assured-guide/module-10/section-01.html)

| Class | What it tests |
| --- | --- |
| [`StubbingBasicsTest`](src/test/java/com/javanibble/module_10/section_01/StubbingBasicsTest.java) | A quote with the provider's rate through `WireMockExtension`, half-even rounding, a request without the right headers matching no stub, calling the stub directly, and invalid input that never reaches the provider |

### Section 2: Provider Faults

**Guide:** [Simulating Provider Faults With WireMock](https://www.javanibble.com/rest-assured-guide/module-10/section-02.html)

| Class | What it tests |
| --- | --- |
| [`ProviderFaultsTest`](src/test/java/com/javanibble/module_10/section_02/ProviderFaultsTest.java) | A server error, a slow provider, a reset connection, broken JSON and a missing rate, rate limiting passed on with `Retry-After`, and an unknown currency |

### Section 3: Verifying Outgoing Calls

**Guide:** [Verifying Outgoing Calls With WireMock](https://www.javanibble.com/rest-assured-guide/module-10/section-03.html)

| Class | What it tests |
| --- | --- |
| [`VerifyingCallsTest`](src/test/java/com/javanibble/module_10/section_03/VerifyingCallsTest.java) | Exactly one call with the right headers per quote, a temporary outage retried once, other errors not retried, and reading the recorded requests yourself |

### Section 4: Response Templates and Scenarios

**Guide:** [WireMock Response Templates and Scenarios](https://www.javanibble.com/rest-assured-guide/module-10/section-04.html)

| Class | What it tests |
| --- | --- |
| [`TemplatingAndScenariosTest`](src/test/java/com/javanibble/module_10/section_04/TemplatingAndScenariosTest.java) | One templated stub for every currency, a provider that fails twice and then recovers, and one failure hidden by the retry |
