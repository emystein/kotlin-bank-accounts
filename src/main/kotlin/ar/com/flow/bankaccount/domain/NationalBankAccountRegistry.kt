package ar.com.flow.bankaccount.domain

interface NationalBankAccountRegistry {
    fun add(anAccount: BankAccount, id: String)
    fun accountById(id: String): BankAccount?
}