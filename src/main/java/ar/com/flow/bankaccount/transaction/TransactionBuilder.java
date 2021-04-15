package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TransactionBuilder {
    private Collection<Precondition> preconditions = new ArrayList<>();
    private List<Algorithm> steps = new ArrayList<>();
    private Money amount;

    public TransactionBuilder precondition(Precondition precondition) {
        preconditions.add(precondition);
        return this;
    }

    public TransactionBuilder step(Algorithm step) {
        steps.add(step);
        return this;
    }

    public TransactionBuilder amount(Money amount) {
        this.amount = amount;
        return this;
    }

    public Transaction build() {
        return new Transaction(new Preconditions(preconditions), steps, amount);
    }
}

