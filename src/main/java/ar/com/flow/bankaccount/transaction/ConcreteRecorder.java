package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;

public class ConcreteRecorder implements Recorder {
    public void add(TransactionRecord record, BankAccount account) {
        account.addTransactionRecord(record);
    }
}
