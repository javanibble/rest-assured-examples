# Module 3: Validating Responses in Depth

This module covers checking any part of a JSON, XML or HTML response precisely: GPath, root paths, numbers and money, the Hamcrest toolbox, response-aware matchers and cookies.

## Table of Contents

- [Module 3: Validating Responses in Depth](#module-3-validating-responses-in-depth)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: Transactions and Bank Statements](#the-domain-transactions-and-bank-statements)
  - [Running the Module](#running-the-module)
  - [Sections](#sections)
    - [Section 1: GPath for JSON](#section-1-gpath-for-json)
    - [Section 2: Root Paths and Path Arguments](#section-2-root-paths-and-path-arguments)
    - [Section 3: Numbers and Money](#section-3-numbers-and-money)
    - [Section 4: The Hamcrest Toolbox](#section-4-the-hamcrest-toolbox)
    - [Section 5: Response-Aware Matchers](#section-5-response-aware-matchers)
    - [Section 6: Cookies](#section-6-cookies)
    - [Section 7: XML and HTML](#section-7-xml-and-html)

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

## Sections

### Section 1: GPath for JSON

**Guide:** [GPath for JSON in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-01.html)

| Class | What it tests |
| --- | --- |
| [`JsonGPathTest`](src/test/java/com/javanibble/module_03/section_01/JsonGPathTest.java) | `find`, `findAll`, conditions on nested objects, `collect`, `any` and `every`, `max` and `min`, indexes and ranges, flattening lists of lists, keys with a dash, server-side filtering, and a JSON array at the root |

### Section 2: Root Paths and Path Arguments

**Guide:** [Root Paths and Path Arguments in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-02.html)

| Class | What it tests |
| --- | --- |
| [`RootPathTest`](src/test/java/com/javanibble/module_03/section_02/RootPathTest.java) | `rootPath`, moving it with `appendRootPath`, `detachRootPath` and `noRootPath`, and path arguments with `withArgs(..)` |

### Section 3: Numbers and Money

**Guide:** [Numbers and Money in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-03.html)

| Class | What it tests |
| --- | --- |
| [`NumbersAndMoneyTest`](src/test/java/com/javanibble/module_03/section_03/NumbersAndMoneyTest.java) | Whole numbers as integers, decimals as `Float` by default and as `BigDecimal` with `JsonConfig`, `equalTo` versus `comparesEqualTo`, exact sums, and `closeTo` |

### Section 4: The Hamcrest Toolbox

**Guide:** [The Hamcrest Toolbox for REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-04.html)

| Class | What it tests |
| --- | --- |
| [`HamcrestToolboxTest`](src/test/java/com/javanibble/module_03/section_04/HamcrestToolboxTest.java) | Matchers for collections, maps and strings, and combining them with `allOf`, `anyOf` and `not` |

### Section 5: Response-Aware Matchers

**Guide:** [Response-Aware Matchers in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-05.html)

| Class | What it tests |
| --- | --- |
| [`ResponseAwareMatcherTest`](src/test/java/com/javanibble/module_03/section_05/ResponseAwareMatcherTest.java) | A `Location` header that must end with the new id, the ready-made response-aware matchers, one body value checked against another, and the whole body |

### Section 6: Cookies

**Guide:** [Response Cookies in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-06.html)

| Class | What it tests |
| --- | --- |
| [`CookiesTest`](src/test/java/com/javanibble/module_03/section_06/CookiesTest.java) | Cookie values, a session cookie locked down with `Secure`, `HttpOnly` and `SameSite` through `detailedCookie(..)`, and reading a cookie afterwards |

### Section 7: XML and HTML

**Guide:** [XML and HTML in REST Assured](https://www.javanibble.com/rest-assured-guide/module-03/section-07.html)

| Class | What it tests |
| --- | --- |
| [`XmlAndHtmlTest`](src/test/java/com/javanibble/module_03/section_07/XmlAndHtmlTest.java) | Elements and attributes of a `camt.053` statement, `find` and `findAll` on elements, depth-first search with `**`, namespaces, and HTML |
