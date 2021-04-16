package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public class Transaction {
    private Preconditions preconditions;
    private final Collection<Algorithm> steps;
    private final Money amount;
    private final List<Algorithm> finishedSteps = new ArrayList<>();

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public void execute() {
        preconditions.check();

        finishedSteps.clear();

        for (Algorithm step : steps) {
            try {
                step.execute(amount);
                finishedSteps.add(step);
            } catch (Exception e) {
                undo();
                return;
            }
        }
    }

    public void undo() {
        finishedSteps.forEach(step -> step.undo(amount));
    }
}
