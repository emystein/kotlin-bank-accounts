package com.emilianomenendez.veritran.bankaccount;

public interface TransactionHistory {
    void add(TransactionRecord record);

    boolean contains(TransactionRecord record);

    boolean containsInOrder(TransactionRecord... records);
}
