package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Number;

import java.time.LocalDateTime;

public class AccountMovement {
    private final LocalDateTime dateTime;
    private final Number amount;

    public AccountMovement(LocalDateTime dateTime, Number amount) {
        this.dateTime = dateTime;
        this.amount = amount;
    }

    public Number getAmount() {
        return amount;
    }
}
