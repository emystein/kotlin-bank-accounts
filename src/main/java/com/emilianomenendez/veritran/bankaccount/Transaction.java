package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Money;

public interface Transaction {
    TransactionRecord execute();

    Money getAmount();
}
