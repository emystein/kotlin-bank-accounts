package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CreateSavingsAccountTest {
    private lateinit var customers: Customers
    private lateinit var bankAccounts: BankAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        bankAccounts = InMemoryBankAccounts()
    }

    @Test
    internal fun createSavingsAccount() {
        val createSavingsAccount = CreateSavingsAccount(customers, bankAccounts)

        val createdAccount = createSavingsAccount.createAccount("Juan Perez", "ARS")

        assertThat(bankAccounts.contains(createdAccount)).isTrue;
    }
}