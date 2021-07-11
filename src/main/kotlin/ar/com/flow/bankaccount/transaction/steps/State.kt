package ar.com.flow.bankaccount.transaction.steps

class State {
    private val executedSteps: MutableList<ExecutedStep> = mutableListOf()

    fun executed(step: ExecutedStep) {
        executedSteps.add(step);
    }

    fun rollback() {
        executedSteps.forEach{ step: ExecutedStep -> step.undo() }
    }
}
