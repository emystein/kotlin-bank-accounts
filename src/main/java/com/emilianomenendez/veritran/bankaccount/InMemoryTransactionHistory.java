package com.emilianomenendez.veritran.bankaccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InMemoryTransactionHistory implements TransactionHistory {
    private List<TransactionRecord> history = new ArrayList<>();

    public int total() {
        return history.size();
    }

    public Optional<TransactionRecord> first() {
        return history.stream().findFirst();
    }

    public void add(TransactionRecord record) {
        history.add(record);
    }

    public boolean contains(TransactionRecord record) {
        return history.contains(record);
    }

    public boolean containsInOrder(TransactionRecord... records) {
        List<TransactionRecord> indexedMovements = Arrays.asList(records);

        return history.stream()
                .filter(indexedMovements::contains)
                .collect(toList())
                .equals(indexedMovements);
    }

    public Optional<Balance> sum() {
        return sum(total());
    }

    public Optional<Balance> sumBeforeLast() {
        return sum(total() - 1);
    }

    private Optional<Balance> sum(int numberOfTransactions) {
        return entries(numberOfTransactions)
                .map(TransactionRecord::getBalance)
                .reduce(Balance::plus);
    }

    private Stream<TransactionRecord> entries(int numberOfEntries) {
        return history.stream().limit(numberOfEntries);
    }
}
