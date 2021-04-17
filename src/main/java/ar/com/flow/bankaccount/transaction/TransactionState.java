package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TransactionState {
    private List<UndoStep> undoSteps = new ArrayList<>();

    public void completed(Algorithm completedStep, Money amount) {
        undoSteps.add(new UndoStep(completedStep, amount));
    }

    public void rollback() {
        undoSteps.forEach(UndoStep::execute);
    }
}
