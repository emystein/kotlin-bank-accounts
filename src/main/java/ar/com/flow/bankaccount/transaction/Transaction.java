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

    public static TransactionBuilder builder() {
        return new TransactionBuilder();
    }

    public void execute() {
        preconditions.check();

        var finishedSteps = new ArrayList<Algorithm>();

        for (Algorithm step : steps) {
            try {
                step.execute(amount);
                finishedSteps.add(step);
            } catch (Exception e) {
                undo(finishedSteps);
                return;
            }
        }
    }

    public void undo(List<Algorithm> finishedSteps) {
        finishedSteps.forEach(step -> step.undo(amount));
    }
}
