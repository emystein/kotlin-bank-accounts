package ar.com.flow.bankaccount.transaction;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class Preconditions implements Precondition {
    private final Collection<Precondition> preconditions;

    public void check() {
        for (Precondition precondition : preconditions) {
            precondition.check();
        }
    }
}
