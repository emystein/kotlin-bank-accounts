package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount

class BankAccountMapper {
    fun toDomain(bankAccount: ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount): BankAccount {
        val owner = CustomerMapper().toDomain(bankAccount.owner)
        return SavingsAccount(owner, bankAccount.currency)
    }

    fun toJpa(bankAccount: BankAccount): ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccount {
        return BankAccount(0, CustomerMapper().toJpa(bankAccount.owner), bankAccount.currency)
    }
}
