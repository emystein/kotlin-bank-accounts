package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class Algorithm {
    protected final BankAccount account;

    void execute(Money amount) {
        account.addTransactionRecord(record(amount));
    }

    protected abstract TransactionRecord record(Money amount);
}
