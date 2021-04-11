package ar.com.flow.bankaccount.transaction;

import java.util.ArrayList;
import java.util.Collection;

public class CompositePreconditions implements Preconditions {
    private final Collection<Preconditions> preconditions = new ArrayList<>();

    public void add(Preconditions preconditions) {
        this.preconditions.add(preconditions);
    }

    public void check() {
        for (Preconditions precondition : preconditions) {
            precondition.check();
        }
    }
}
