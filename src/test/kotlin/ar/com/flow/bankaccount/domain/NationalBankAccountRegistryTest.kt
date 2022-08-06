package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NationalBankAccountRegistryTest {
    private val bbvaBankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

    private lateinit var danielsBbvaUsdAccount: BankAccount

    @BeforeEach
    internal fun setUp() {
        danielsBbvaUsdAccount = bbvaBankAccounts.createSavingsAccount(TestObjects.daniel, Currency.USD)
    }

    @Test
    fun addAccountToRegistry() {
        val registry = InMemoryNationalBankAccountRegistry()

        registry.add(danielsBbvaUsdAccount, "1")

        assertThat(registry.accountById("1")).isEqualTo(danielsBbvaUsdAccount)
    }
}