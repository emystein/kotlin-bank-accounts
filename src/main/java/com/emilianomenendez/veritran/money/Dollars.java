package com.emilianomenendez.veritran.money;

public class Dollars extends PositiveAmount {
    public static Dollars amount(int amount) {
        return new Dollars(amount);
    }

    private Dollars(int amount) {
        super("USD", amount);
    }
}
