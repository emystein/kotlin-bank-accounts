package com.emilianomenendez.veritran.bankaccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class InMemoryAccountHistory implements AccountHistory {
    private List<AccountMovement> history = new ArrayList<>();

    public void add(AccountMovement movement) {
        history.add(movement);
    }

    public boolean contains(AccountMovement movement) {
        return history.contains(movement);
    }

    public boolean containsInOrder(AccountMovement... movements) {
        List<AccountMovement> indexedMovements = Arrays.asList(movements);

        return history.stream()
                .filter(indexedMovements::contains)
                .collect(toList())
                .equals(indexedMovements);
    }

    public Stream<AccountMovement> allMovements() {
        return history.stream();
    }
}
