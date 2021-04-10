package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;

public interface TransactionLog {
    // TODO: move account as field of TransactionRecord ?
    void add(TransactionRecord record, BankAccount account);
}
