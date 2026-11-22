# Module 6: Schema and Contract Validation

This module covers checking the shape of a response instead of only its values: JSON Schema (draft 2020-12), an RFC 9457 error contract, XML Schema for ISO 20022 payment files, and checking every response against an OpenAPI description.

## Table of Contents

- [Module 6: Schema and Contract Validation](#module-6-schema-and-contract-validation)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Payment Initiation](#the-domain-payment-initiation)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-6-schema-and-contract-validation). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. JSON Schema | [Validate Responses Against a JSON Schema in REST Assured](https://www.javanibble.com/rest-assured-guide/module6-section1/) |
| 2. An Error Contract | [Test an RFC 9457 Error Contract in REST Assured](https://www.javanibble.com/rest-assured-guide/module6-section2/) |
| 3. XML Schema | [Validate ISO 20022 XML Against an XSD in REST Assured](https://www.javanibble.com/rest-assured-guide/module6-section3/) |
| 4. An OpenAPI Contract Filter | [Check Every Response Against OpenAPI With a REST Assured Filter](https://www.javanibble.com/rest-assured-guide/module6-section4/) |

## The Domain: Payment Initiation

[`PaymentInitiationApi`](src/main/java/com/javanibble/pisp/PaymentInitiationApi.java) is a payment initiation API in the PSD2 PISP style, with a documented contract. All endpoints are under `/pisp/v1`.

| Request | Response |
| --- | --- |
| `POST /domestic-payments`, JSON | 201, a payment, or a 400, 415 or 422 problem |
| `GET /domestic-payments/{id}` | 200, a payment, or a 404 problem |
| `GET /domestic-payments/{id}?format=v0` | 200, the payment in an old shape that breaks the contract, to show what schema validation catches |
| `POST /payment-files`, `application/xml` ISO 20022 `pain.001` | 202, a `pain.002` status report: `ACCP`, or `RJCT` when the declared number of transactions is wrong |

The contract is in [`src/test/resources`](src/test/resources):

| File | What it describes |
| --- | --- |
| [`schemas/payment.schema.json`](src/test/resources/schemas/payment.schema.json) | A payment, as JSON Schema draft 2020-12 |
| [`schemas/problem.schema.json`](src/test/resources/schemas/problem.schema.json) | Every error response (RFC 9457) |
| [`xsd/pain.001.001.09-simplified.xsd`](src/test/resources/xsd/pain.001.001.09-simplified.xsd) and [`xsd/pain.002.001.10-simplified.xsd`](src/test/resources/xsd/pain.002.001.10-simplified.xsd) | The payment file and its status report |
| [`openapi/payment-initiation.openapi.json`](src/test/resources/openapi/payment-initiation.openapi.json) | The whole API, as OpenAPI 3.1 |

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-06` | 18 tests pass |
