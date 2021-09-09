package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.ports.out.Receipts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SavingsAccountFactory(@Autowired private val receipts: Receipts) {
    fun createSavingsAccount(owner: Customer, currency: Currency): SavingsAccount {
        return SavingsAccount(owner, currency, Statement(owner, currency, receipts))
    }
}