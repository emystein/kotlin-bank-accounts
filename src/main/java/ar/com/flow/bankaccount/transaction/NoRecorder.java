package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;

public class NoRecorder implements Recorder {
    public void add(TransactionRecord record, BankAccount account) {

    }
}
