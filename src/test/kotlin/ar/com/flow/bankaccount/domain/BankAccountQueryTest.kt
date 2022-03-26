package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.domain.Currency.USD
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.TestObjects.mabel
import ar.com.flow.bankaccount.ports.out.BankAccountFilters
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.TestMoney
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BankAccountQueryTest {
    private val bankAccounts: BankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

    private lateinit var danielsUSDAccount: BankAccount
    private lateinit var danielsARSAccount: BankAccount
    private lateinit var mabelsUSDAccount: BankAccount

    @BeforeEach
    fun setUp() {
        danielsUSDAccount = bankAccounts.createSavingsAccount(daniel, USD)
        danielsUSDAccount.deposit(TestMoney.dollars100)

        danielsARSAccount = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        danielsARSAccount.deposit(TestMoney.ars100)

        mabelsUSDAccount = bankAccounts.createSavingsAccount(mabel, USD)
        mabelsUSDAccount.deposit(TestMoney.dollars100)
    }

    @Test
    fun shouldFilterAccountsOwnedByAGivenUser() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().owner(daniel))

        assertThat(foundAccounts).isNotEmpty()
        foundAccounts.forEach { account ->
            assertThat(account.owner).isEqualTo(daniel)
        }
    }

    @Test
    fun shouldFilterAccountsHoldingAGivenCurrency() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().currency(USD))

        assertThat(foundAccounts).isNotEmpty()
        foundAccounts.forEach { account ->
            assertThat(account.currency).isEqualTo(USD)
        }
    }

    @Test
    fun shouldFilterAccountsOwnedByAGivenUserAndAGivenCurrency() {
        val foundAccounts = bankAccounts.query(BankAccountFilters()
            .owner(daniel)
            .currency(USD)
        )

        foundAccounts.forEach { account ->
            assertThat(account.owner).isEqualTo(daniel)
            assertThat(account.currency).isEqualTo(USD)
        }
    }
}