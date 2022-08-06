package ar.com.flow.bankaccount.domain

interface BankRegistry {
    fun add(bankToAdd: Bank)
    fun contains(aBank: Bank): Boolean
}