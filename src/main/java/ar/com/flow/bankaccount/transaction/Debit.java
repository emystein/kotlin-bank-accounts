package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;

public class Debit extends Algorithm {
    private final Action action;

    public Debit(BankAccount account, Action action) {
        super(account);
        this.action = action;
    }

    protected TransactionRecord record(Money amount) {
        return TransactionRecord.debit(account, action, amount);
    }
}
