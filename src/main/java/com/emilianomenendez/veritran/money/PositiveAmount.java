package com.emilianomenendez.veritran.money;

import com.emilianomenendez.veritran.NegativeAmountException;

public class PositiveAmount extends Money {
    public PositiveAmount(String currency, int amount) {
        super(currency, amount);

        if (amount < 0) {
            throw new NegativeAmountException(amount);
        }
    }
}
