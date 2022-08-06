package ar.com.flow.bankaccount.domain

class InMemoryBankRegistry : BankRegistry {
    private val banks: MutableList<Bank> = mutableListOf()

    override fun add(bankToAdd: Bank) {
        banks.add(bankToAdd)
    }

    override fun contains(aBank: Bank): Boolean {
        return banks.contains(aBank)
    }
}
