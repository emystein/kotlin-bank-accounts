package ar.com.flow.bankaccount.domain.transaction

import ar.com.flow.bankaccount.domain.transaction.steps.Step
import ar.com.flow.money.Money

class Builder {
    private val preconditions: MutableList<Precondition> = ArrayList()
    private val steps: MutableList<Step> = ArrayList()
    private lateinit var amount: Money

    fun precondition(precondition: Precondition): Builder {
        preconditions.add(precondition)
        return this
    }

    fun step(step: Step): Builder {
        steps.add(step)
        return this
    }

    fun steps(vararg steps: Step): Builder {
        steps.forEach { step -> this.steps.add(step) }
        return this
    }

    fun amount(amount: Money): Builder {
        this.amount = amount
        return this
    }

    fun build(): Transaction {
        return Transaction(preconditions, steps, amount)
    }
}