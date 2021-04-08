package ar.com.flow.bankaccount;

import java.util.Optional;
import java.util.stream.Stream;

public interface TransactionHistory {
    boolean isEmpty();

    int total();

    Optional<TransactionRecord> first();

    void add(TransactionRecord record);

    boolean contains(TransactionRecord record);

    boolean containsInOrder(TransactionRecord... records);

    Optional<Balance> sum();

    Optional<Balance> sumBeforeLast();

    Stream<TransactionRecord> stream();
}
