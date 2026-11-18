# Module 2: Building Requests

This module covers every way to put data into a request: path, query and form parameters, headers, cookies, request bodies and multipart uploads, and how to send every HTTP method.

## Table of Contents

- [Module 2: Building Requests](#module-2-building-requests)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Payments](#the-domain-payments)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-02/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Path Parameters | [Path Parameters in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-01.html) |
| 2. Query, Form and Plain Parameters | [Query, Form and Plain Parameters in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-02.html) |
| 3. Headers and Cookies | [Headers, Cookies and Content Types in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-03.html) |
| 4. Request Bodies | [Request Bodies in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-04.html) |
| 5. Every HTTP Method | [Every HTTP Method in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-05.html) |
| 6. Multipart Uploads | [Multipart Uploads in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-06.html) |

## The Domain: Payments

[`PaymentsApi`](src/main/java/com/javanibble/payments/PaymentsApi.java) accepts every kind of request data, so the tests can practise building requests. All endpoints are under `/api/v1`.

| Request | Response |
| --- | --- |
| `POST /payments`, JSON, header `X-Request-ID` | 201 with `Location`, or 400, 415 or 422 |
| `GET /payments?status=..&currency=..` | 200 `{"count":..,"payments":[..]}`; `status` may be repeated |
| `QUERY /payments`, a JSON filter body | 200, the same shape as `GET` |
| `GET /payments/{id}` | 200 or 404 |
| `PUT /payments/{id}`, JSON | 200, or 409 when the payment is cancelled |
| `PATCH /payments/{id}`, `application/merge-patch+json` | 200, changes `reference` |
| `DELETE /payments/{id}` | 204, the payment is cancelled |
| `POST /payments/bulk`, multipart: `batchName` and a CSV `file` | 201 |
| `POST /beneficiaries`, form URL-encoded | 201 |
| Any method on `/echo`, `/echo/{a}` and `/echo/{a}/{b}` | 200, a JSON description of the request as the server received it |

`HEAD` works for every `GET` route, and `OPTIONS` for every path. [`SamplePayments`](src/test/java/com/javanibble/module_02/support/SamplePayments.java) builds valid payment bodies for the tests.

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-02` | 54 tests pass |
