package ar.com.flow.bankaccount;

import ar.com.flow.money.Money;

import static java.time.LocalDateTime.now;

public abstract class BaseTransaction implements Transaction {
    private final Preconditions preconditions;
    private final TransactionAlgorithm algorithm;

    public abstract BankAccount account();

    public abstract Money getAmount();

    public abstract TransactionRecord transactionRecord();

    public BaseTransaction(Preconditions preconditions, TransactionAlgorithm algorithm) {
        this.preconditions = preconditions;
        this.algorithm = algorithm;
    }

    public TransactionRecord execute() {
        preconditions.check();

        algorithm.execute();

        return recordTransaction();
    }

    public TransactionRecord recordTransaction() {
        TransactionRecord record = transactionRecord();

        account().addTransactionRecord(record);

        return record;
    }

    public TransactionRecord transactionRecord(TransactionReason reason, Balance balance) {
        return new TransactionRecord(now(), reason, balance);
    }
}
