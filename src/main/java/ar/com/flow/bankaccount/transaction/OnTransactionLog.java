package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;

public class OnTransactionLog implements TransactionLog {
    public void add(TransactionRecord record, BankAccount account) {
        account.addTransactionRecord(record);
    }
}
