# Module 2: Building Requests

This module covers every way to put data into a request: path, query and form parameters, headers, cookies, request bodies and multipart uploads, and how to send every HTTP method.

## Table of Contents

- [Module 2: Building Requests](#module-2-building-requests)
  - [Table of Contents](#table-of-contents)
  - [The Domain: Payments](#the-domain-payments)
  - [Running the Module](#running-the-module)
  - [Guides](#guides)
  - [Sections](#sections)
    - [Section 1: Path Parameters](#section-1-path-parameters)
    - [Section 2: Query, Form and Plain Parameters](#section-2-query-form-and-plain-parameters)
    - [Section 3: Headers and Cookies](#section-3-headers-and-cookies)
    - [Section 4: Request Bodies](#section-4-request-bodies)
    - [Section 5: Every HTTP Method](#section-5-every-http-method)
    - [Section 6: Multipart Uploads](#section-6-multipart-uploads)

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

## Sections

### Section 1: Path Parameters

**Guide:** [Path Parameters in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-01.html)

| Class | What it tests |
| --- | --- |
| [`PathParametersTest`](src/test/java/com/javanibble/module_02/section_01/PathParametersTest.java) | A named path parameter, several from a map, unnamed parameters filled in order, named and unnamed mixed, URL encoding of values, and a real payment resource |

### Section 2: Query, Form and Plain Parameters

**Guide:** [Query, Form and Plain Parameters in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-02.html)

| Class | What it tests |
| --- | --- |
| [`ParametersTest`](src/test/java/com/javanibble/module_02/section_02/ParametersTest.java) | One and several query parameters, multi-value and no-value parameters, `java.time` values, URL encoding (and double encoding, and turning it off), form parameters as a URL-encoded body, and what `param` does for GET and POST |

### Section 3: Headers and Cookies

**Guide:** [Headers, Cookies and Content Types in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-03.html)

| Class | What it tests |
| --- | --- |
| [`HeadersAndCookiesTest`](src/test/java/com/javanibble/module_02/section_03/HeadersAndCookiesTest.java) | One header, several from a map or as objects, multi-value headers, merging and overwriting with `HeaderConfig`, `accept(..)`, the charset REST Assured adds and how to turn it off, and cookies |

### Section 4: Request Bodies

**Guide:** [Request Bodies in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-04.html)

| Class | What it tests |
| --- | --- |
| [`RequestBodiesTest`](src/test/java/com/javanibble/module_02/section_04/RequestBodiesTest.java) | A JSON string, a `Map`, a record and a `List` as the body, field errors for an invalid body, the wrong content type, and bytes and files sent as they are |

### Section 5: Every HTTP Method

**Guide:** [Every HTTP Method in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-05.html)

| Class | What it tests |
| --- | --- |
| [`HttpMethodsTest`](src/test/java/com/javanibble/module_02/section_05/HttpMethodsTest.java) | `post`, `get`, `put`, `patch`, `delete`, `head` and `options` on one payment, the `QUERY` method (REST Assured 6.1), `request(Method, path)`, and a method the resource doesn't support |

### Section 6: Multipart Uploads

**Guide:** [Multipart Uploads in REST Assured](https://www.javanibble.com/rest-assured-guide/module-02/section-06.html)

| Class | What it tests |
| --- | --- |
| [`MultipartUploadTest`](src/test/java/com/javanibble/module_02/section_06/MultipartUploadTest.java) | Uploading a bulk payment file, bytes with a file name, a part built with `MultiPartSpecBuilder`, the `multipart/form-data` request, and a missing part |
