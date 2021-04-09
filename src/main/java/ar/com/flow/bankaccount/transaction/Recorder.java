package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;

public interface Recorder {
    void add(TransactionRecord record, BankAccount account);
}
