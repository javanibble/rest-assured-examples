# Module 6: Schema and Contract Validation

This module covers checking the shape of a response instead of only its values: JSON Schema (draft 2020-12), an RFC 9457 error contract, XML Schema for ISO 20022 payment files, and checking every response against an OpenAPI description.

## Table of Contents

- [Module 6: Schema and Contract Validation](#module-6-schema-and-contract-validation)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Payment Initiation](#the-domain-payment-initiation)
  - [Running the Module](#running-the-module)
  - [Sections](#sections)
    - [Section 1: JSON Schema](#section-1-json-schema)
    - [Section 2: An Error Contract](#section-2-an-error-contract)
    - [Section 3: XML Schema](#section-3-xml-schema)
    - [Section 4: An OpenAPI Contract Filter](#section-4-an-openapi-contract-filter)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-06/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. JSON Schema | [JSON Schema Validation in REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-01.html) |
| 2. An Error Contract | [An Error Contract in REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-02.html) |
| 3. XML Schema | [XML Schema Validation in REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-03.html) |
| 4. An OpenAPI Contract Filter | [An OpenAPI Contract Filter for REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-04.html) |

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

## Sections

### Section 1: JSON Schema

**Guide:** [JSON Schema Validation in REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-01.html)

| Class | What it tests |
| --- | --- |
| [`JsonSchemaTest`](src/test/java/com/javanibble/module_06/section_01/JsonSchemaTest.java) | A created and a read payment matching the schema, a response that breaks the contract, an inline schema, and validating JSON without an HTTP call |

### Section 2: An Error Contract

**Guide:** [An Error Contract in REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-02.html)

| Class | What it tests |
| --- | --- |
| [`ErrorContractTest`](src/test/java/com/javanibble/module_06/section_02/ErrorContractTest.java) | Not found, malformed JSON, the wrong content type and validation errors, all checked against one problem schema, and a schema that is itself strict about errors |

### Section 3: XML Schema

**Guide:** [XML Schema Validation in REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-03.html)

| Class | What it tests |
| --- | --- |
| [`XmlSchemaTest`](src/test/java/com/javanibble/module_06/section_03/XmlSchemaTest.java) | Valid test data, an accepted and a rejected `pain.001` file with `matchesXsdInClasspath`, and an XSD that rejects broken documents |

### Section 4: An OpenAPI Contract Filter

**Guide:** [An OpenAPI Contract Filter for REST Assured](https://www.javanibble.com/rest-assured-guide/module-06/section-04.html)

| Class | What it tests |
| --- | --- |
| [`OpenApiContractTest`](src/test/java/com/javanibble/module_06/section_04/OpenApiContractTest.java) | Initiating and reading a payment, documented errors, a body that breaks the contract, and an undocumented operation |
| [`OpenApiContractFilter`](src/test/java/com/javanibble/module_06/support/OpenApiContractFilter.java) | Checks the operation, status, content type and body of every response against the OpenAPI file |
