package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TransactionState {
    private final Money amount;
    private List<Algorithm> completedSteps = new ArrayList<>();

    public void complete(Algorithm completedStep) {
        completedSteps.add(completedStep);
    }

    public void rollback() {
        completedSteps.forEach(step -> step.undo(amount));
    }
}
