package com.emilianomenendez.veritran.money;

public interface Number {
    int getAmount();

    boolean isGreaterThanOrEqual(Number other);

    boolean isLessThan(Number other);

    Number plus(Number other);

    Number minus(Number other);
}
