package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Credit implements RecordFactory {
    private final BankAccount account;
    private final Action action;

    public TransactionRecord record(Money amount) {
        return TransactionRecord.credit(account, action, amount);
    }
}
