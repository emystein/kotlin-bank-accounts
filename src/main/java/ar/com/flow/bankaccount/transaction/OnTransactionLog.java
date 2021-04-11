package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OnTransactionLog implements TransactionLog {
    private final BankAccount account;

    public void add(TransactionRecord record) {
        account.addTransactionRecord(record);
    }
}
