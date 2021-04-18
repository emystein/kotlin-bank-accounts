package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class Transaction {
    private Collection<Precondition> preconditions;
    private final Collection<Step> steps;
    private final Money amount;

    public static Builder builder() {
        return new Builder();
    }

    public void execute() {
        preconditions.forEach(Precondition::check);

        var state = new State();

        for (Step step : steps) {
            try {
                step.execute(amount);
                state.completed(step, amount);
            } catch (Exception e) {
                state.rollback();
                return;
            }
        }
    }
}
