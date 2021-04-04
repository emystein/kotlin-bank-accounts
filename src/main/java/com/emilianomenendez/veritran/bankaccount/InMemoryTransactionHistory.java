package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InMemoryTransactionHistory implements TransactionHistory {
    private List<TransactionRecord> history = new ArrayList<>();

    public TransactionRecord first() {
        return history.get(0);
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

    public int totalTransactions() {
        return history.size();
    }

    public Balance sum() {
        return sum(totalTransactions());
    }

    public Balance sumBeforeLastTransaction() {
        return sum(totalTransactions() - 1);
    }

    private Balance sum(int numberOfTransactions) {
        return entries(numberOfTransactions)
                .map(TransactionRecord::getAmount)
                .reduce(Balance.positive(Dollars.amount(0)), Balance::sum);
    }

    public Stream<TransactionRecord> entries(int numberOfEntries) {
        return history.stream().limit(numberOfEntries);
    }
}
