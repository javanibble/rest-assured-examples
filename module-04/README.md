# Module 4: Extracting Values and Mapping Objects

This module covers getting data out of a response, as single values or as Java objects, and feeding it into the next request: `extract()`, `JsonPath` and `XmlPath`, mapping to records, `TypeRef`, configuring Jackson 3, and chaining requests.

## Table of Contents

- [Module 4: Extracting Values and Mapping Objects](#module-4-extracting-values-and-mapping-objects)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Customers and Consents](#the-domain-customers-and-consents)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-4-extracting-values-and-mapping-objects). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Extracting Values | [Extract Values From a Response in REST Assured](https://www.javanibble.com/rest-assured-guide/module4-section1/) |
| 2. JsonPath and XmlPath | [Read Many Values With JsonPath and XmlPath in REST Assured](https://www.javanibble.com/rest-assured-guide/module4-section2/) |
| 3. Mapping to Java Records | [Map Responses to Java Records in REST Assured](https://www.javanibble.com/rest-assured-guide/module4-section3/) |
| 4. Configuring the Object Mapper | [Configure the Object Mapper in REST Assured](https://www.javanibble.com/rest-assured-guide/module4-section4/) |
| 5. Chaining Requests | [Chain Requests Into an API Flow in REST Assured](https://www.javanibble.com/rest-assured-guide/module4-section5/) |

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
