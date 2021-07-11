package ar.com.flow.bankaccount.domain.transaction

interface Precondition {
    fun check()
}