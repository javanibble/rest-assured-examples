package com.javanibble.accounts;

/** The body of {@code POST /api/v1/accounts}. */
public record NewAccount(String iban, String owner, String currency) {
}
