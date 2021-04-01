package com.emilianomenendez.veritran.money;

public interface Money {
    String getCurrency();

    int getAmount();

    boolean isGreaterThanOrEqual(Money other);

    boolean isLessThan(Money other);

    Money plus(Money other);

    Money minus(Money other);
}
