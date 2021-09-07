package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemorySavingsAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DepositTest {
    private lateinit var customers: Customers
    private lateinit var savingsAccounts: SavingsAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        savingsAccounts = InMemorySavingsAccounts()
    }

    @Test
    internal fun deposit() {
        val juanPerez = customers.save(Customer("Juan Perez"))
        savingsAccounts.create(juanPerez, currency = "ARS")

        val deposit = Deposit(customers, savingsAccounts)

        deposit.execute(customerName = "Juan Perez", currency = "ARS", amountToDeposit = 100);

        val savingsAccount = savingsAccounts.accountOwnedBy(juanPerez, "ARS")!!
        assertThat(savingsAccount.balance).isEqualTo(Balance("ARS", 100));
    }
}