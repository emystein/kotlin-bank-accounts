package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.Currency.USD
import ar.com.flow.bankaccount.domain.TestObjects
import ar.com.flow.bankaccount.domain.TestObjects.daniel
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
    private lateinit var usdAccounts: MutableList<BankAccount>

    @BeforeEach
    fun setUp() {
        danielsUSDAccount = bankAccounts.createSavingsAccount(daniel, USD)
        danielsUSDAccount.deposit(TestMoney.dollars100)

        danielsARSAccount = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        danielsARSAccount.deposit(TestMoney.ars100)

        danielsAccounts = mutableListOf(danielsUSDAccount, danielsARSAccount)

        mabelsUSDAccount = bankAccounts.createSavingsAccount(TestObjects.mabel, USD)
        mabelsUSDAccount.deposit(TestMoney.dollars100)

        usdAccounts = mutableListOf(danielsUSDAccount, mabelsUSDAccount)
    }


    @Test
    fun shouldFilterAccountsOwnedByAGivenUser() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().owner(daniel))

        assertThat(foundAccounts).hasSameSizeAs(danielsAccounts)
        danielsAccounts.forEach { account ->
            assertThat(foundAccounts).contains(account)
        }
    }

    @Test
    fun shouldFilterAccountsHoldingAGivenCurrency() {
        val foundAccounts = bankAccounts.query(BankAccountFilters().currency(USD))

        assertThat(foundAccounts).isEqualTo(usdAccounts)
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