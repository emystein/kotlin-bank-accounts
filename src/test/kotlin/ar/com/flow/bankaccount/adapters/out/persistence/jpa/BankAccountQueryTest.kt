package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects
import ar.com.flow.bankaccount.ports.out.BankAccountFilters
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.TestMoney
import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.hasSameSizeAs
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration


@DataJpaTest
@ContextConfiguration(classes = [BankAccountPersistenceConfiguration::class])
class BankAccountQueryTest {
    @Autowired
    private lateinit var bankAccounts: BankAccounts

    private lateinit var danielsUSDAccount: BankAccount
    private lateinit var danielsARSAccount: BankAccount
    private lateinit var mabelsUSDAccount: BankAccount

    private lateinit var danielsAccounts: MutableList<BankAccount>
    private lateinit var danielsUSDAccounts: MutableList<BankAccount>
    private lateinit var usdAccounts: MutableList<BankAccount>

    @BeforeEach
    fun setUp() {
        danielsUSDAccount = bankAccounts.createSavingsAccount(TestObjects.daniel, Currency.USD)
        danielsUSDAccount.deposit(TestMoney.dollars100)

        danielsARSAccount = bankAccounts.createSavingsAccount(TestObjects.daniel, Currency.ARS)
        danielsARSAccount.deposit(TestMoney.ars100)

        danielsAccounts = mutableListOf(danielsUSDAccount, danielsARSAccount)
        danielsUSDAccounts = mutableListOf(danielsUSDAccount)

        mabelsUSDAccount = bankAccounts.createSavingsAccount(TestObjects.mabel, Currency.USD)
        mabelsUSDAccount.deposit(TestMoney.dollars100)

        usdAccounts = mutableListOf(danielsUSDAccount, mabelsUSDAccount)
    }


    @Test
    fun shouldFilterAccountsOwnedByAGivenUser() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().owner(TestObjects.daniel))

        assertThat(foundAccounts).hasSameSizeAs(danielsAccounts)
        danielsAccounts.forEach { account ->
            assertThat(foundAccounts).contains(account)
        }
    }

    @Test
    fun shouldFilterAccountsHoldingAGivenCurrency() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().currency(Currency.USD))

        assertThat(foundAccounts).isEqualTo(usdAccounts)
    }

    @Test
    fun shouldFilterAccountsOwnedByAGivenUserAndAGivenCurrency() {
        val foundAccounts = bankAccounts.query(BankAccountFilters()
            .owner(TestObjects.daniel)
            .currency(Currency.USD)
        )

        assertThat(foundAccounts).hasSameSizeAs(danielsUSDAccounts)
        danielsUSDAccounts.forEach { account ->
            assertThat(foundAccounts).contains(account)
        }
    }
}