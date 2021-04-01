package com.emilianomenendez.veritran.bankaccount;

import java.util.stream.Stream;

public interface AccountHistory {
    void add(AccountMovement movement);

    boolean contains(AccountMovement movement);

    boolean containsInOrder(AccountMovement... movements);

    Stream<AccountMovement> allMovements();
}
