package com.akka.transaction;

/**
 * Created by root on 15-2-8.
 */
public class AccountDebit {
    private Float amount;

    private String message;

    public AccountDebit(Float amount, String message) {
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
