package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;

public abstract class Algorithm {
    public abstract BankAccount getAccount();

    public abstract TransactionRecord record(Money amount);

    void execute(Money amount) {
        getAccount().addTransactionRecord(record(amount));
    }
}
