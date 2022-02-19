package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.doesNotContain
import assertk.assertions.isEmpty
import assertk.assertions.isTrue
import org.junit.jupiter.api.Test

class InMemoryBankAccountsTest {
    @Test
    internal fun createAccount() {
        val bankAccounts = InMemoryBankAccounts()

        val createdAccount = bankAccounts.create(daniel, Currency.ARS)

        assertThat(bankAccounts.contains(createdAccount)).isTrue()
    }

    @Test
    internal fun accountNotFound() {
        val bankAccounts = InMemoryBankAccounts()

        assertThat(bankAccounts.ownedBy(daniel, Currency.ARS)).isEmpty()
    }

    @Test
    fun allSavingsAccountsOwnedByCustomer() {
        val bankAccounts = InMemoryBankAccounts()

        val arsAccount = bankAccounts.create(daniel, Currency.ARS)
        val usdAccount = bankAccounts.create(daniel, Currency.USD)

        assertThat(bankAccounts.ownedBy(daniel)).containsAll(arsAccount, usdAccount)
    }

    @Test
    fun noSavingsAccountsOwnedByCustomer() {
        val bankAccounts = InMemoryBankAccounts()

        assertThat(bankAccounts.ownedBy(daniel)).isEmpty()
    }

    @Test
    fun allSavingsAccountsOwnedByCustomerForAGivenCurrency() {
        val bankAccounts = InMemoryBankAccounts()

        val arsAccount1 = bankAccounts.create(daniel, Currency.ARS)
        val arsAccount2 = bankAccounts.create(daniel, Currency.ARS)
        val usdAccount = bankAccounts.create(daniel, Currency.USD)

        val arsAccounts = bankAccounts.ownedBy(daniel, Currency.ARS)

        assertThat(arsAccounts).containsAll(arsAccount1, arsAccount2)
        assertThat(arsAccounts).doesNotContain(usdAccount)
    }
}