package ar.com.flow.bankaccount.domain

class InMemoryNationalBankAccountRegistry : NationalBankAccountRegistry {
    private val accountsById = mutableMapOf<String, BankAccount>()

    override fun add(anAccount: BankAccount, id: String) {
        accountsById[id] = anAccount
    }

    override fun accountById(id: String): BankAccount? {
        return accountsById[id]
    }
}
