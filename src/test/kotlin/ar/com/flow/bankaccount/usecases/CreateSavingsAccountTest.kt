package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import assertk.assertThat
import assertk.assertions.isTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateSavingsAccountTest {
    private lateinit var customers: Customers
    private lateinit var bankAccounts: BankAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        bankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())
    }

    @Test
    internal fun createSavingsAccount() {
        val createSavingsAccount = CreateSavingsAccount(customers, bankAccounts)

        val createdAccount = createSavingsAccount.execute("Juan Perez", "ARS")

        assertThat(bankAccounts.contains(createdAccount)).isTrue()
    }
}