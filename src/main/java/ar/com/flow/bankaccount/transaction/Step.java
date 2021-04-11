package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Step {
    private final Algorithm algorithm;
    private final TransactionLog log;

    public TransactionRecord execute(Money amount) {
        return algorithm.execute(amount);
    }

    public void log(TransactionRecord record) {
        log.add(record);
    }
}
