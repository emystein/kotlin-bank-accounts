package ar.com.flow.bankaccount;

import ar.com.flow.money.Money;

public interface Transaction {
    Money getAmount();

    TransactionRecord execute();
}
