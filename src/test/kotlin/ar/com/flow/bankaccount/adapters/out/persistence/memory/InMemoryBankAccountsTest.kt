package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
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
        val bankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

        val createdAccount = bankAccounts.createSavingsAccount(daniel, Currency.ARS)

        assertThat(bankAccounts.contains(createdAccount)).isTrue()
    }

    @Test
    internal fun accountNotFound() {
        val bankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

        assertThat(bankAccounts.ownedBy(daniel, Currency.ARS)).isEmpty()
    }

    @Test
    fun allSavingsAccountsOwnedByCustomer() {
        val bankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

        val arsAccount = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        val usdAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)

        assertThat(bankAccounts.ownedBy(daniel)).containsAll(arsAccount, usdAccount)
    }

    @Test
    fun noSavingsAccountsOwnedByCustomer() {
        val bankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

        assertThat(bankAccounts.ownedBy(daniel)).isEmpty()
    }

    @Test
    fun allSavingsAccountsOwnedByCustomerForAGivenCurrency() {
        val bankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

        val arsAccount1 = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        val arsAccount2 = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        val usdAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)

        val arsAccounts = bankAccounts.ownedBy(daniel, Currency.ARS)

        assertThat(arsAccounts).containsAll(arsAccount1, arsAccount2)
        assertThat(arsAccounts).doesNotContain(usdAccount)
    }
}