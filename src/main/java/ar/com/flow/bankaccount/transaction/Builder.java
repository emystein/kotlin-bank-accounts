package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;

import java.util.ArrayList;
import java.util.List;

public class Builder {
    private List<Precondition> preconditions = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private Money amount;

    public Builder precondition(Precondition precondition) {
        preconditions.add(precondition);
        return this;
    }

    public Builder step(Step step) {
        steps.add(step);
        return this;
    }

    public Builder amount(Money amount) {
        this.amount = amount;
        return this;
    }

    public Transaction build() {
        return new Transaction(preconditions, steps, amount);
    }
}

