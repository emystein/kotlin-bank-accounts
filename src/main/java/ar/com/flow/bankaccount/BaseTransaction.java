package ar.com.flow.bankaccount;

import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
public abstract class BaseTransaction {
    private final BankAccount account;
    private final Preconditions preconditions;
    private final TransactionAlgorithm algorithm;
    private final TransactionReason reason;
    private final Balance amount;

    public void execute() {
        preconditions.check();

        algorithm.execute();

        account.addTransactionRecord(new TransactionRecord(now(), reason, amount));
    }
}
