# Module 10: Stubbing Dependencies with WireMock

This module covers testing an API whose own dependency you don't control, by replacing it with a WireMock stub: stubbing and request matching, faults and slowness, verifying outgoing calls, and response templates and stateful scenarios.

## Table of Contents

- [Module 10: Stubbing Dependencies with WireMock](#module-10-stubbing-dependencies-with-wiremock)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: A Quote Service and an FX Provider](#the-domain-a-quote-service-and-an-fx-provider)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-10-stubbing-dependencies-with-wiremock). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Stubbing a Dependency | [Stub a Dependency With WireMock in REST Assured Tests](https://www.javanibble.com/rest-assured-guide/module10-section1/) |
| 2. Provider Faults | [Simulate Provider Faults With WireMock in REST Assured Tests](https://www.javanibble.com/rest-assured-guide/module10-section2/) |
| 3. Verifying Outgoing Calls | [Verify Outgoing Calls With WireMock in REST Assured Tests](https://www.javanibble.com/rest-assured-guide/module10-section3/) |
| 4. Response Templates and Scenarios | [Use Response Templates and Scenarios in WireMock Stubs](https://www.javanibble.com/rest-assured-guide/module10-section4/) |

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
