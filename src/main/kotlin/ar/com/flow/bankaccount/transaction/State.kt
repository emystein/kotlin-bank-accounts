package ar.com.flow.bankaccount.transaction

import ar.com.flow.money.Money
import java.util.function.Consumer

internal class State {
    private val undoSteps: MutableList<UndoStep> = ArrayList()

    fun completed(completedStep: Step, amount: Money) {
        undoSteps.add(UndoStep(completedStep, amount))
    }

    fun rollback() {
        undoSteps.forEach(Consumer { obj: UndoStep -> obj.execute() })
    }
}