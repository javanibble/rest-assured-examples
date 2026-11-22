# Module 3: Validating Responses in Depth

This module covers checking any part of a JSON, XML or HTML response precisely: GPath, root paths, numbers and money, the Hamcrest toolbox, response-aware matchers and cookies.

## Table of Contents

- [Module 3: Validating Responses in Depth](#module-3-validating-responses-in-depth)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Transactions and Bank Statements](#the-domain-transactions-and-bank-statements)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/rest-assured-learning-guide/#module-3-validating-responses-in-depth). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. GPath for JSON | [Filter JSON Lists With GPath in REST Assured](https://www.javanibble.com/rest-assured-guide/module3-section1/) |
| 2. Root Paths and Path Arguments | [Shorten JSON Paths With Root Paths in REST Assured](https://www.javanibble.com/rest-assured-guide/module3-section2/) |
| 3. Numbers and Money | [Check Money Exactly With BigDecimal in REST Assured](https://www.javanibble.com/rest-assured-guide/module3-section3/) |
| 4. The Hamcrest Toolbox | [Pick the Right Hamcrest Matcher in REST Assured](https://www.javanibble.com/rest-assured-guide/module3-section4/) |
| 5. Response-Aware Matchers | [Check Server-Generated Values With Response-Aware Matchers](https://www.javanibble.com/rest-assured-guide/module3-section5/) |
| 6. Cookies | [Check Session Cookie Security in REST Assured](https://www.javanibble.com/rest-assured-guide/module3-section6/) |
| 7. XML and HTML | [Check XML and HTML Responses in REST Assured](https://www.javanibble.com/rest-assured-guide/module3-section7/) |

## The Domain: Transactions and Bank Statements

[`TransactionsApi`](src/main/java/com/javanibble/transactions/TransactionsApi.java) serves one account with a month of transactions as JSON, as an ISO 20022 style XML bank statement and as an HTML page. All endpoints are under `/api/v1`.

| Request | Response |
| --- | --- |
| `GET /accounts/{id}/transactions?type=DEBIT\|CREDIT` | 200 JSON, or 404 for an unknown account |
| `POST /accounts/{id}/transactions` | 201 with a `Location` ending in the new id |
| `GET /accounts/{id}/statement` | 200 `application/xml`, a simplified `camt.053` |
| `GET /accounts/{id}/statement.html` | 200 `text/html` |
| `GET /categories` | 200, a JSON array at the root |
| `POST /sessions` | 201 with two `Set-Cookie` headers |

Amounts are JSON numbers with two decimals; debits are negative.

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-03` | 38 tests pass |
