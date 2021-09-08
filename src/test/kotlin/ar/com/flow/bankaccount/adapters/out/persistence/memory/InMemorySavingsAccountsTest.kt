package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.domain.TestObjects.francisco
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InMemorySavingsAccountsTest {
    @Test
    internal fun createAccount() {
        val bankAccounts = InMemorySavingsAccounts()

        val createdAccount = bankAccounts.create(francisco, "ARS")

        assertTrue(bankAccounts.contains(createdAccount))
    }

    @Test
    internal fun accountNotFound() {
        val bankAccounts = InMemorySavingsAccounts()

        assertThat(bankAccounts.accountOwnedBy(francisco, "ARS")).isEmpty
    }
}