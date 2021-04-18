package ar.com.flow.bankaccount.transaction;

import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class State {
    private List<UndoStep> undoSteps = new ArrayList<>();

    void completed(Step completedStep, Money amount) {
        undoSteps.add(new UndoStep(completedStep, amount));
    }

    void rollback() {
        undoSteps.forEach(UndoStep::execute);
    }
}
