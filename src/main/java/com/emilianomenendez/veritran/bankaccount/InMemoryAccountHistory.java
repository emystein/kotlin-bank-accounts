package com.emilianomenendez.veritran.bankaccount;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                .filter(movement -> indexedMovements.contains(movement))
                .collect(toList())
                .equals(indexedMovements);
    }
}
