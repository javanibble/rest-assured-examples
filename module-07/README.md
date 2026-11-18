# Module 7: Authentication and Transport Security

This module covers testing protected APIs, including what must happen when credentials are missing, wrong or insufficient: Basic authentication, OAuth 2.0 bearer tokens, negative security tests, sessions and CSRF, TLS and mutual TLS, and credentials on redirects.

## Table of Contents

- [Module 7: Authentication and Transport Security](#module-7-authentication-and-transport-security)
  - [Table of Contents](#table-of-contents)
  - [Guides](#guides)
  - [The Domain: A Protected Bank](#the-domain-a-protected-bank)
  - [Running the Module](#running-the-module)

## Guides

Each section has a step-by-step guide in the [Java Nibble REST Assured Guide](https://www.javanibble.com/rest-assured-guide/module-07/). The guides explain the concepts behind the tests, walk through the code, and point out common mistakes. Read a section's guide, then run and change its tests.

| Section | Guide |
| --- | --- |
| 1. Basic Authentication | [Basic Authentication in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-01.html) |
| 2. OAuth 2.0 Bearer Tokens | [OAuth 2.0 Bearer Tokens in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-02.html) |
| 3. Negative Security Tests | [Negative Security Tests in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-03.html) |
| 4. Sessions and CSRF | [Sessions and CSRF in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-04.html) |
| 5. TLS and Mutual TLS | [TLS and Mutual TLS in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-05.html) |
| 6. Credentials on Redirects | [Credentials on Redirects in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-06.html) |

## The Domain: A Protected Bank

[`BankAuthApi`](src/main/java/com/javanibble/auth/BankAuthApi.java) is an OAuth 2.0 authorization server and the resource server it protects, in one process, with tokens signed by [`JwtService`](src/main/java/com/javanibble/auth/JwtService.java).

| Request | Rules |
| --- | --- |
| `POST /oauth2/token` | The client credentials grant, with `client_secret_basic` or `client_secret_post`; errors follow RFC 6749 |
| `GET /.well-known/jwks.json` | The public signing key |
| `GET /api/v1/accounts` | Needs `accounts:read` |
| `POST /api/v1/payments` | Needs `payments:write` |
| `GET /admin/health` | Basic `admin`/`changeit`; 401 with a `Basic` challenge without it |
| `GET /admin/report` | The same credentials, but 404 without them, as APIs that hide resources do |
| `GET` and `POST /portal/login`, `GET /portal/home` | A form login with a CSRF token, and a page that needs the `JSESSIONID` session cookie |
| `GET /api/v1/partner-redirect` | 302 to a partner server on another port |

The clients are `tpp-accounts`/`accounts-secret` (may get `accounts:read`) and `tpp-payments`/`payments-secret` (may get `accounts:read` and `payments:write`). Token errors follow RFC 6750: 401 with `WWW-Authenticate: Bearer ...` for a missing or invalid token, and 403 with `error="insufficient_scope"` for a missing scope.

[`SecureAccountsApi`](src/main/java/com/javanibble/auth/SecureAccountsApi.java) serves `GET /api/v1/whoami` over HTTPS with a self-signed certificate for `localhost`, optionally requiring a client certificate. The key stores are in [`src/main/resources/tls`](src/main/resources/tls) and [`src/test/resources/tls`](src/test/resources/tls).

## Running the Module

| Goal | Command | Result |
| --- | --- | --- |
| Run the module | `mvn test -pl module-07` | 34 tests pass |
