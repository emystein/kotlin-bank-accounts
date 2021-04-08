package ar.com.flow.bankaccount;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@Getter
public abstract class BaseTransaction implements Transaction {
    private final BankAccount account;
    private final Preconditions preconditions;
    private final TransactionAlgorithm algorithm;
    private final TransactionReason reason;
    private final Balance amount;

    public TransactionRecord execute() {
        preconditions.check();

        algorithm.execute();

        return recordTransaction();
    }

    public TransactionRecord recordTransaction() {
        TransactionRecord record = new TransactionRecord(now(), reason, amount);

        account.addTransactionRecord(record);

        return record;
    }
}
