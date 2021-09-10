package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InMemorySavingsAccountsTest {
    @Test
    internal fun createAccount() {
        val bankAccounts = InMemoryBankAccounts()

        val createdAccount = bankAccounts.create(daniel, Currency.ARS)

        assertTrue(bankAccounts.contains(createdAccount))
    }

    @Test
    internal fun accountNotFound() {
        val bankAccounts = InMemoryBankAccounts()

        assertThat(bankAccounts.ownedBy(daniel, Currency.ARS)).isEmpty()
    }
}