package com.emilianomenendez.veritran.bankaccount;

import java.util.Optional;

public interface TransactionHistory {
    int total();

    Optional<TransactionRecord> first();

    void add(TransactionRecord record);

    boolean contains(TransactionRecord record);

    boolean containsInOrder(TransactionRecord... records);

    Optional<Balance> sum();

    Optional<Balance> sumBeforeLast();
}
