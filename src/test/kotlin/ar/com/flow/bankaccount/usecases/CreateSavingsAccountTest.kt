package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemorySavingsAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateSavingsAccountTest {
    private lateinit var customers: Customers
    private lateinit var savingsAccounts: SavingsAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        savingsAccounts = InMemorySavingsAccounts()
    }

    @Test
    internal fun createSavingsAccount() {
        val createSavingsAccount = CreateSavingsAccount(customers, savingsAccounts)

        val createdAccount = createSavingsAccount.createAccount("Juan Perez", "ARS")

        assertThat(savingsAccounts.contains(createdAccount)).isTrue;
    }
}