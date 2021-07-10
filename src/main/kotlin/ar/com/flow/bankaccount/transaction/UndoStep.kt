package ar.com.flow.bankaccount.transaction

import ar.com.flow.money.Money

internal class UndoStep(private val step: Step, private val amount: Money) {
    fun execute() {
        step.undo(amount)
    }
}