package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class Transaction {
    private Preconditions preconditions;
    private final Collection<Step> steps;
    private final Money amount;

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public static class TransactionBuilder {
        private List<Preconditions> preconditions = new ArrayList<>();
        private List<Step> steps = new ArrayList<>();
        private Money amount;

        public TransactionBuilder precondition(Preconditions precondition) {
            preconditions.add(precondition);
            return this;
        }

        public TransactionBuilder step(Step step) {
            steps.add(step);
            return this;
        }

        public TransactionBuilder amount(Money amount) {
            this.amount = amount;
            return this;
        }

        public Transaction build() {
            return new Transaction(new CompositePreconditions(preconditions), steps, amount);
        }
    }

    public void execute() {
        preconditions.check();

        for (Step step : steps) {
            step.execute(amount);
        }
    }
}
