package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Step {
    private final Algorithm algorithm;
    private final BankAccount accountToLog;

    public void execute(Money amount) {
        var record = algorithm.execute(amount);
        accountToLog.addTransactionRecord(record);
    }
}
