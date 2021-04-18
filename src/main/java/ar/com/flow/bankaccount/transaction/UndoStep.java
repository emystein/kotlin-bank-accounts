package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UndoStep {
    private final Step step;
    private final Money amount;

    void execute() {
        step.undo(amount);
    }
}
