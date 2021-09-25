package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class WithdrawalTest {
    private lateinit var customers: Customers
    private lateinit var bankAccounts: BankAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        bankAccounts = InMemoryBankAccounts()
    }

    @Test
    internal fun withdraw() {
        val juanPerez = customers.save(Customer("Juan Perez"))
        bankAccounts.create(juanPerez, currency = Currency.ARS)

        val deposit = Deposit(customers, bankAccounts)
        deposit.execute(customerName = "Juan Perez", currencyCode = Currency.ARS.code, amountToDeposit = 100)

        val withdrawal = Withdrawal(customers, bankAccounts)
        withdrawal.execute(customerName = "Juan Perez", Currency.ARS.code, amountToWithdraw = 100)

        val savingsAccount = bankAccounts.ownedBy(juanPerez, Currency.ARS).first()
        assertThat(savingsAccount.balance).isEqualTo(Balance(Currency.ARS, 0))
    }
}