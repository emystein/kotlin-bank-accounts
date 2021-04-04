package com.emilianomenendez.veritran.bankaccount;

public interface Transaction {
    TransactionRecord execute();

    com.emilianomenendez.veritran.money.Money getAmount();
}
