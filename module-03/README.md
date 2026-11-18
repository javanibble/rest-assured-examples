# Module 3: Validating Responses in Depth

This module covers checking any part of a JSON, XML or HTML response precisely: GPath, root paths, numbers and money, the Hamcrest toolbox, response-aware matchers and cookies.

## Table of Contents

- [Module 3: Validating Responses in Depth](#module-3-validating-responses-in-depth)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Transactions and Bank Statements](#the-domain-transactions-and-bank-statements)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-03/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. GPath for JSON | [GPath for JSON in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-01.html) |
| 2. Root Paths and Path Arguments | [Root Paths and Path Arguments in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-02.html) |
| 3. Numbers and Money | [Numbers and Money in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-03.html) |
| 4. The Hamcrest Toolbox | [The Hamcrest Toolbox for REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-04.html) |
| 5. Response-Aware Matchers | [Response-Aware Matchers in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-05.html) |
| 6. Cookies | [Response Cookies in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-06.html) |
| 7. XML and HTML | [XML and HTML in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-07.html) |

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
