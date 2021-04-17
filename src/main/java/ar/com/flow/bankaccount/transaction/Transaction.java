package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
public class Transaction {
    private Preconditions preconditions;
    private final Collection<Algorithm> steps;
    private final Money amount;

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public void execute() {
        preconditions.check();

        var state = new TransactionState(amount);

        for (Algorithm step : steps) {
            try {
                step.execute(amount);
                state.complete(step);
            } catch (Exception e) {
                state.rollback();
                return;
            }
        }
    }
}
