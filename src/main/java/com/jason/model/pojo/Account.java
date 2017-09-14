package com.jason.model.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jason.model.json.AccountDeserializer;

import java.math.BigDecimal;

@JsonDeserialize(using = AccountDeserializer.class)
public class Account {
    private Long accountId;
    private String customerName;
    private String currency;
    private BigDecimal amount;

    public Account() {
    }

    Account(Long accountId, String customerName, String currency, BigDecimal amount) {
        this.accountId = accountId;
        this.customerName = customerName;
        this.currency = currency;
        this.amount = amount;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
