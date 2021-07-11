package ar.com.flow.bankaccount.domain.transaction

import ar.com.flow.bankaccount.domain.transaction.steps.State
import ar.com.flow.bankaccount.domain.transaction.steps.Step
import ar.com.flow.money.Money

class Transaction(
    private val preconditions: Collection<Precondition>,
    private val steps: List<Step>,
    private val amount: Money,
) {

    fun execute() {
        preconditions.forEach{ precondition -> precondition.check() }

        val state = State()

        for (step in steps) {
            try {
                val executedStep = step.execute(amount)
                state.addExecutedStep(executedStep)
            } catch (exception: Exception) {
                state.rollback()
                return
            }
        }
    }
}