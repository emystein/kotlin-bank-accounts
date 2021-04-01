package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;

import java.time.LocalDateTime;

public class AccountMovement {
    private final LocalDateTime dateTime;
    private final Money amount;

    public AccountMovement(LocalDateTime dateTime, Money amount) {
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public String getCurrency() {
        return amount.getCurrency();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Money getAmount() {
        return amount;
    }
}
