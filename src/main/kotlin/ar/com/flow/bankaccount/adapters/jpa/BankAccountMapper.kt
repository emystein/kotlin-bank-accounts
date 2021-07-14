package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.SavingsAccount

class BankAccountMapper {
    fun toDomain(jpaBankAccount: JpaBankAccount): BankAccount {
        val owner = CustomerMapper().toDomain(jpaBankAccount.owner)
        return SavingsAccount(owner, jpaBankAccount.currency)
    }

    fun toJpa(bankAccount: BankAccount): JpaBankAccount {
        return JpaBankAccount(0, CustomerMapper().toJpa(bankAccount.owner), bankAccount.currency)
    }
}
