package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Debit implements RecordFactory {
    private final BankAccount account;
    private final Action action;

    public TransactionRecord record(Money amount) {
        return TransactionRecord.debit(account, action, amount);
    }
}
