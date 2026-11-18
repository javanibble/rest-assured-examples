# Module 4: Extracting Values and Mapping Objects

This module covers getting data out of a response, as single values or as Java objects, and feeding it into the next request: `extract()`, `JsonPath` and `XmlPath`, mapping to records, `TypeRef`, configuring Jackson 3, and chaining requests.

## Table of Contents

- [Module 4: Extracting Values and Mapping Objects](#module-4-extracting-values-and-mapping-objects)
  - [Table of Contents](#table-of-contents)
  - [The Domain: Customers and Consents](#the-domain-customers-and-consents)
  - [Running the Module](#running-the-module)
  - [Guides](#guides)
  - [Sections](#sections)
    - [Section 1: Extracting Values](#section-1-extracting-values)
    - [Section 2: JsonPath and XmlPath](#section-2-jsonpath-and-xmlpath)
    - [Section 3: Mapping to Java Records](#section-3-mapping-to-java-records)
    - [Section 4: Configuring the Object Mapper](#section-4-configuring-the-object-mapper)
    - [Section 5: Chaining Requests](#section-5-chaining-requests)

## The Domain: Customers and Consents

[`ConsentsApi`](src/main/java/com/javanibble/consents/ConsentsApi.java) is modelled on open banking account access consents (PSD2 AIS): a customer grants a consent with permissions, authorises it, and the consent id then unlocks their accounts. All endpoints are under `/api/v1`.

| Request | Response |
| --- | --- |
| `POST /customers` | 201 with `Location`, a customer |
| `GET /customers/{id}` | 200 or 404 |
| `GET /customers?page=0&size=2` | 200, a page: `items`, `page`, `size`, `totalItems`, `totalPages` |
| `POST /consents` | 201, status `AWAITING_AUTHORISATION`, with `_links` |
| `GET /consents/{id}` | 200 or 404 |
| `POST /consents/{id}/authorisation` | 200, status `AUTHORISED` |
| `GET /accounts`, header `Consent-ID` | 200, the customer's accounts; 400 without the header, 403 unless the consent is authorised with `ReadAccountsBasic` |
| `GET /accounts/{id}/balances`, header `Consent-ID` | 200; needs `ReadBalances` |

[`Models`](src/test/java/com/javanibble/module_04/support/Models.java) holds the records the tests map responses to.

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-04` | 21 tests pass |

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-04/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Extracting Values | [Extracting Values in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-01.html) |
| 2. JsonPath and XmlPath | [JsonPath and XmlPath in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-02.html) |
| 3. Mapping to Java Records | [Mapping Responses to Java Records in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-03.html) |
| 4. Configuring the Object Mapper | [Configuring the Object Mapper in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-04.html) |
| 5. Chaining Requests | [Chaining Requests in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-05.html) |

## Sections

### Section 1: Extracting Values

**Guide:** [Extracting Values in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-01.html)

| Class | What it tests |
| --- | --- |
| [`ExtractingValuesTest`](src/test/java/com/javanibble/module_04/section_01/ExtractingValuesTest.java) | `extract().path(..)` for a value and a list, headers and the status code of an extracted response, `extract().response()`, and a value from one response used in the next request |

### Section 2: JsonPath and XmlPath

**Guide:** [JsonPath and XmlPath in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-02.html)

| Class | What it tests |
| --- | --- |
| [`JsonPathAndXmlPathTest`](src/test/java/com/javanibble/module_04/section_02/JsonPathAndXmlPathTest.java) | Typed getters, `getMap` and `getObject`, `JsonPath` on JSON that didn't come from HTTP, and `XmlPath`, which works the same way |

### Section 3: Mapping to Java Records

**Guide:** [Mapping Responses to Java Records in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-03.html)

| Class | What it tests |
| --- | --- |
| [`ObjectMappingTest`](src/test/java/com/javanibble/module_04/section_03/ObjectMappingTest.java) | A record out, a record in and a record out, a generic page with `TypeRef`, a JSON array as a `List`, and unknown fields ignored by default |

### Section 4: Configuring the Object Mapper

**Guide:** [Configuring the Object Mapper in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-04.html)

| Class | What it tests |
| --- | --- |
| [`ObjectMapperConfigurationTest`](src/test/java/com/javanibble/module_04/section_04/ObjectMapperConfigurationTest.java) | Choosing Jackson 3 explicitly with `ObjectMapperType.JACKSON_3`, and a lenient and a strict `JsonMapper` through `ObjectMapperConfig` |

### Section 5: Chaining Requests

**Guide:** [Chaining Requests in REST Assured](https://www.javanibble.com/rest-assured-guide/module-04/section-05.html)

| Class | What it tests |
| --- | --- |
| [`ChainingRequestsTest`](src/test/java/com/javanibble/module_04/section_05/ChainingRequestsTest.java) | Create, then follow the `Location` header; the full consent flow; a consent that isn't authorised; and a consent that grants only its own permissions |
