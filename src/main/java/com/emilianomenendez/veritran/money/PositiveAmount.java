package com.emilianomenendez.veritran.money;

public class PositiveAmount extends Money {
    public PositiveAmount(String currency, int amount) {
        super(currency, amount);

        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be non-negative: " + amount);
        }
    }
}
