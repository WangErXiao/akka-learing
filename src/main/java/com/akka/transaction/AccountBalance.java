package com.akka.transaction;

/**
 * Created by root on 15-2-8.
 */
public class AccountBalance {
    private String accountNumber;

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    private Float amount;

    public AccountBalance(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountBalance(String accountNumber, Float amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Float getAmount() {
        return amount;
    }
}
