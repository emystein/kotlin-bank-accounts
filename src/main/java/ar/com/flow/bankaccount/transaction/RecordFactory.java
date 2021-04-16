package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

public interface RecordFactory {
    TransactionRecord record(Money amount);
}
