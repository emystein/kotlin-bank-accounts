package com.emilianomenendez.veritran.bankaccount;

public class NegativeBalance extends Balance {
    public NegativeBalance(String currency, int amount) {
        super(currency, Math.min(-amount, amount));
    }

    public String getSign() {
        return "-";
    }
}
