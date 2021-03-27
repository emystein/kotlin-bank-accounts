package com.emilianomenendez.veritran.money;

public interface Number {
    int getAmount();

    boolean isLessThan(Number amountToWithdraw);

    Number plus(Number other);

    Number minus(Number other);
}
