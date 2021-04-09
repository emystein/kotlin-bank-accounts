package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

public interface Algorithm {
    TransactionRecord execute(Money amount);
}
