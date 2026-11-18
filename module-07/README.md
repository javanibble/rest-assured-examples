# Module 7: Authentication and Transport Security

This module covers testing protected APIs, including what must happen when credentials are missing, wrong or insufficient: Basic authentication, OAuth 2.0 bearer tokens, negative security tests, sessions and CSRF, TLS and mutual TLS, and credentials on redirects.

## Table of Contents

- [Module 7: Authentication and Transport Security](#module-7-authentication-and-transport-security)
  - [Table of Contents](#table-of-contents)
  - [The Domain: A Protected Bank](#the-domain-a-protected-bank)
  - [Running the Module](#running-the-module)
  - [Guides](#guides)
  - [Sections](#sections)
    - [Section 1: Basic Authentication](#section-1-basic-authentication)
    - [Section 2: OAuth 2.0 Bearer Tokens](#section-2-oauth-20-bearer-tokens)
    - [Section 3: Negative Security Tests](#section-3-negative-security-tests)
    - [Section 4: Sessions and CSRF](#section-4-sessions-and-csrf)
    - [Section 5: TLS and Mutual TLS](#section-5-tls-and-mutual-tls)
    - [Section 6: Credentials on Redirects](#section-6-credentials-on-redirects)

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

## Sections

### Section 1: Basic Authentication

**Guide:** [Basic Authentication in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-01.html)

| Class | What it tests |
| --- | --- |
| [`BasicAuthenticationTest`](src/test/java/com/javanibble/module_07/section_01/BasicAuthenticationTest.java) | The server's challenge, challenged Basic answering it, preemptive Basic, challenged Basic failing where the server never challenges, and wrong credentials |

### Section 2: OAuth 2.0 Bearer Tokens

**Guide:** [OAuth 2.0 Bearer Tokens in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-02.html)

| Class | What it tests |
| --- | --- |
| [`BearerTokenTest`](src/test/java/com/javanibble/module_07/section_02/BearerTokenTest.java) | The token response, `client_secret_post`, `auth().oauth2(token)` and a plain `Authorization` header, and reading the claims of the JWT |

### Section 3: Negative Security Tests

**Guide:** [Negative Security Tests in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-03.html)

| Class | What it tests |
| --- | --- |
| [`NegativeSecurityTest`](src/test/java/com/javanibble/module_07/section_03/NegativeSecurityTest.java) | At the token endpoint: a wrong secret, an unsupported grant type and a scope the client may not have. At the resource server: every endpoint without a token, expired, tampered and wrong-audience tokens, a missing scope, and the right scope |

### Section 4: Sessions and CSRF

**Guide:** [Sessions and CSRF in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-04.html)

| Class | What it tests |
| --- | --- |
| [`SessionAndCsrfTest`](src/test/java/com/javanibble/module_07/section_04/SessionAndCsrfTest.java) | A new session, logging in and using the session with `SessionFilter`, no session, setting the session id explicitly, a missing CSRF token, and a CSRF token that works only once |

### Section 5: TLS and Mutual TLS

**Guide:** [TLS and Mutual TLS in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-05.html)

| Class | What it tests |
| --- | --- |
| [`TlsAndMutualTlsTest`](src/test/java/com/javanibble/module_07/section_05/TlsAndMutualTlsTest.java) | An untrusted certificate, `relaxedHTTPSValidation()` and what it hides, a trust store, mutual TLS without and with a client certificate from a key store, and a JDK `SSLContext` |

### Section 6: Credentials on Redirects

**Guide:** [Credentials on Redirects in REST Assured](https://www.javanibble.com/rest-assured-guide/module-07/section-06.html)

| Class | What it tests |
| --- | --- |
| [`CredentialsOnRedirectTest`](src/test/java/com/javanibble/module_07/section_06/CredentialsOnRedirectTest.java) | Credentials dropped on a cross-origin redirect (REST Assured 6.1), and turning the old behaviour back on |
