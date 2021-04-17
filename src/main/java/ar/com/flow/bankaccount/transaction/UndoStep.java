package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UndoStep {
    private final Step step;
    private final Money amount;

    public void execute() {
        step.undo(amount);
    }
}
