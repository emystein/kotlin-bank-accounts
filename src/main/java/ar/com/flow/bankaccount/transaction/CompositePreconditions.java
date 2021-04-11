package ar.com.flow.bankaccount.transaction;

import lombok.RequiredArgsConstructor;

import java.util.Collection;

@RequiredArgsConstructor
public class CompositePreconditions implements Preconditions {
    private final Collection<Preconditions> preconditions;

    public void check() {
        for (Preconditions precondition : preconditions) {
            precondition.check();
        }
    }
}
