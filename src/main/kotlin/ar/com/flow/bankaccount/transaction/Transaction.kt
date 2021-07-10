package ar.com.flow.bankaccount.transaction

import ar.com.flow.money.Money
import java.util.function.Consumer

class Transaction(
    private val preconditions: Collection<Precondition>,
    private val steps: Collection<Step>,
    private val amount: Money
) {

    fun execute() {
        preconditions.forEach(Consumer { obj: Precondition -> obj.check() })

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

    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }
    }
}