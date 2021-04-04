package com.emilianomenendez.veritran.bankaccount;

public interface AccountHistory {
    void add(AccountMovement movement);

    boolean contains(AccountMovement movement);

    boolean containsInOrder(AccountMovement... movements);
}
