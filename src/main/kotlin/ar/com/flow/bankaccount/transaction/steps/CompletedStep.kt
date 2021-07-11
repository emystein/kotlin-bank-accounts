package ar.com.flow.bankaccount.transaction.steps

import ar.com.flow.money.Money

class CompletedStep(private val step: Step, private val amount: Money) {
    fun undo() {
        step.revoke(amount)
    }
}
