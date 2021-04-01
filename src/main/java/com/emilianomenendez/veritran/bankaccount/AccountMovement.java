package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Money;
import com.emilianomenendez.veritran.money.Number;

import java.time.LocalDateTime;

public class AccountMovement {
    private final LocalDateTime dateTime;
    private final String currency;
    private final Number amount;

    public AccountMovement(LocalDateTime dateTime, String currency, Number amount) {
        this.dateTime = dateTime;
        this.currency = currency;
        this.amount = amount;
    }

    public AccountMovement(LocalDateTime dateTime, Money money) {
        this(dateTime, money.getCurrency(), money);
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Number getAmount() {
        return amount;
    }
}
