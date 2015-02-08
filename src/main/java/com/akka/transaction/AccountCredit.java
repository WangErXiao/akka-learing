package com.akka.transaction;

/**
 * Created by root on 15-2-8.
 */
public class AccountCredit {
    private Float amount;

    private String message;

    public AccountCredit(Float amount, String message) {
        this.amount = amount;
        this.message = message;
    }


    public Float getAmount() {
        return amount;
    }

    public String getMessage() {
        return message;
    }
}
