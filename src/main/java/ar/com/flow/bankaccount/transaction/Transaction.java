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

        for (Algorithm step : steps) {
            step.execute(amount);
        }
    }
}
