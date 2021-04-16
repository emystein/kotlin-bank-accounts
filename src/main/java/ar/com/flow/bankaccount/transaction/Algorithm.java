package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Algorithm {
    private final BankAccount account;
    private final RecordFactory recordFactory;

    void execute(Money amount) {
        account.addTransactionRecord(recordFactory.record(amount));
    }
}
