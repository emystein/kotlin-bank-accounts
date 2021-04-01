package com.emilianomenendez.veritran.bankaccount;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryAccountHistory implements AccountHistory {
    private List<AccountMovement> history = new ArrayList<>();

    @Override
    public void add(AccountMovement movement) {
        history.add(movement);
    }

    @Override
    public boolean contains(AccountMovement movement) {
        return history.contains(movement);
    }

    @Override
    public boolean containsInOrder(AccountMovement... movements) {
        List<Integer> indices = Stream.of(movements).map(movement -> history.indexOf(movement)).sorted().collect(Collectors.toList());
        List<Integer> sortedIndices = Stream.of(movements).map(movement -> history.indexOf(movement)).collect(Collectors.toList());
        return indices.equals(sortedIndices);
    }
}
