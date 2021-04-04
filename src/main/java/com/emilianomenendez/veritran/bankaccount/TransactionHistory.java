package com.emilianomenendez.veritran.bankaccount;

public interface TransactionHistory {
    TransactionRecord first();

    void add(TransactionRecord record);

    boolean contains(TransactionRecord record);

    boolean containsInOrder(TransactionRecord... records);

    int totalTransactions();

    Balance sum();

    Balance sumBeforeLastTransaction();
}
