package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.TransactionRecord;

import java.util.Optional;

public interface TransactionHistory {
    int total();

    Optional<TransactionRecord> first();

    Optional<TransactionRecord> last();

    void add(TransactionRecord record);

    boolean contains(TransactionRecord record);

    boolean containsInOrder(TransactionRecord... records);

    Optional<Balance> getCurrentBalance();

    Optional<Balance> getPreviousBalance();

    Optional<Balance> getInitialBalance();
}
