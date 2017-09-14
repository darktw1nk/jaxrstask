package com.jason.model.pojo;

import java.math.BigDecimal;

public class AccountBuilder {
    private Long accountId;
    private String customerName;
    private String currency;
    private BigDecimal amount;

    public AccountBuilder setAccountId(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    public AccountBuilder setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public AccountBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public AccountBuilder setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Account createAccount() {
        return new Account(accountId, customerName, currency, amount);
    }
}