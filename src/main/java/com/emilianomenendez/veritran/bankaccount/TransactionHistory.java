package com.emilianomenendez.veritran.bankaccount;

public interface TransactionHistory {
    int total();

    TransactionRecord first();

    void add(TransactionRecord record);

    boolean contains(TransactionRecord record);

    boolean containsInOrder(TransactionRecord... records);

    Balance sum();

    Balance sumBeforeLast();
}
