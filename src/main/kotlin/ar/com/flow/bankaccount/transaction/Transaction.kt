package ar.com.flow.bankaccount.transaction

import ar.com.flow.money.Money

class Transaction(
    private val preconditions: Collection<Precondition>,
    private val steps: List<Step>,
    private val amount: Money
) {

    fun execute() {
        preconditions.forEach{ precondition -> precondition.check() }

        val state = State()

        for (step in steps) {
            try {
                step.execute(amount)
                state.completed(step, amount)
            } catch (e: Exception) {
                state.rollback()
                return
            }
        }
    }
}