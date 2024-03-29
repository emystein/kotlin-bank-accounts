package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.TestMoney.ars100
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountPersistenceConfiguration::class])
class SavingsAccountsTest {
    @Autowired
    private lateinit var bankAccounts: BankAccounts

    @Test
    fun createSavingsAccount() {
        val account = bankAccounts.createSavingsAccount(daniel, Currency.ARS)

        assertThat(bankAccounts.ownedBy(daniel, Currency.ARS)).contains(account)
    }

    @Test
    fun updateSavingsAccount() {
        val account = bankAccounts.createSavingsAccount(daniel, Currency.ARS)

        account.deposit(ars100)

        bankAccounts.save(account)

        val updatedAccount = bankAccounts.ownedBy(daniel, Currency.ARS).first()
        assertThat(updatedAccount.balance).isEqualTo(Balance.positive(ars100))
    }

    @Test
    fun containsCreatedAccount() {
        val account = bankAccounts.createSavingsAccount(daniel, Currency.ARS)

        assertThat(bankAccounts.contains(account)).isTrue()
    }

    @Test
    fun transientAccountNotFound() {
        assertThat(bankAccounts.ownedBy(daniel)).isEmpty()
    }

    @Test
    fun allSavingsAccountsOwnedByCustomer() {
        val arsAccount = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        val usdAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)

        assertThat(bankAccounts.ownedBy(daniel)).containsAll(arsAccount, usdAccount)
    }


    @Test
    fun allSavingsAccountsOwnedByCustomerForAGivenCurrency() {
        val arsAccount1 = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        val arsAccount2 = bankAccounts.createSavingsAccount(daniel, Currency.ARS)
        val usdAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)

        val arsAccounts = bankAccounts.ownedBy(daniel, Currency.ARS)

        assertThat(arsAccounts).containsAll(arsAccount1, arsAccount2)
        assertThat(arsAccounts).doesNotContain(usdAccount)
    }
}