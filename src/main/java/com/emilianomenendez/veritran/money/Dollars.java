package com.emilianomenendez.veritran.money;

public class Dollars extends Money {
    public static Dollars amount(int amount) {
        return new Dollars(amount);
    }

    private Dollars(int amount) {
        super("USD", amount);
    }
}
