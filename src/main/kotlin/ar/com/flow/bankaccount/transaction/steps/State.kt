package ar.com.flow.bankaccount.transaction.steps

import ar.com.flow.money.Money

internal class State {
    private val completedSteps: MutableList<CompletedStep> = ArrayList()

    fun completed(stepToComplete: Step, amount: Money) {
        completedSteps.add(CompletedStep(stepToComplete, amount))
    }

    fun rollback() {
        completedSteps.forEach{ step: CompletedStep -> step.undo() }
    }
}
