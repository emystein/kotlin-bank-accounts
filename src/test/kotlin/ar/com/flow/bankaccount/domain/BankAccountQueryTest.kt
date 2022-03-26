package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.TestObjects.mabel
import ar.com.flow.bankaccount.ports.out.BankAccountFilters
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.TestMoney
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSameSizeAs
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BankAccountQueryTest {
    private val bankAccounts: BankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

    private lateinit var danielsUSDAccount: BankAccount
    private lateinit var danielsARSAccount: BankAccount
    private lateinit var mabelsUSDAccount: BankAccount

    private lateinit var danielsAccounts: MutableList<BankAccount>
    private lateinit var danielsUSDAccounts: MutableList<BankAccount>
    private lateinit var usdAccounts: MutableList<BankAccount>

    @BeforeEach
    fun setUp() {
        danielsUSDAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)
        danielsUSDAccount.deposit(TestMoney.dollars100)

        danielsARSAccount = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        danielsARSAccount.deposit(TestMoney.ars100)

        danielsAccounts = mutableListOf(danielsUSDAccount, danielsARSAccount)
        danielsUSDAccounts = mutableListOf(danielsUSDAccount)

        mabelsUSDAccount = bankAccounts.createSavingsAccount(mabel, Currency.USD)
        mabelsUSDAccount.deposit(TestMoney.dollars100)

        usdAccounts = mutableListOf(danielsUSDAccount, mabelsUSDAccount)
    }

    @Test
    fun shouldFilterAccountsOwnedByAGivenUser() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().owner(daniel))

        assertThat(foundAccounts).isEqualTo(danielsAccounts)
    }

    @Test
    fun shouldFilterAccountsHoldingAGivenCurrency() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().currency(Currency.USD))

        assertThat(foundAccounts).isEqualTo(usdAccounts)
    }

    @Test
    fun shouldFilterAccountsOwnedByAGivenUserAndAGivenCurrency() {
        val foundAccounts = bankAccounts.query(BankAccountFilters()
            .owner(daniel)
            .currency(Currency.USD)
        )

        assertThat(foundAccounts).hasSameSizeAs(danielsUSDAccounts)
        danielsUSDAccounts.forEach { account ->
            assertThat(foundAccounts).contains(account)
        }
    }
}