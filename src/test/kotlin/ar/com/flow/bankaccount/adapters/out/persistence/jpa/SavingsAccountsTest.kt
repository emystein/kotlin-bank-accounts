package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.TestMoney.ars100
import org.assertj.core.api.Assertions.assertThat
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
        val account = bankAccounts.create(daniel, Currency.ARS)

        assertThat(bankAccounts.ownedBy(daniel, Currency.ARS)).contains(account)
    }

    @Test
    fun updateSavingsAccount() {
        val account = bankAccounts.create(daniel, Currency.ARS)
        account.deposit(ars100)

        bankAccounts.save(account)

        val updatedAccount = bankAccounts.ownedBy(daniel, Currency.ARS).first()
        assertThat(updatedAccount.balance).isEqualTo(Balance.positive(ars100))
    }

    @Test
    fun containsCreatedAccount() {
        val account = bankAccounts.create(daniel, Currency.ARS)

        assertThat(bankAccounts.contains(account)).isTrue
    }

    @Test
    fun transientAccountNotFound() {
        assertThat(bankAccounts.ownedBy(daniel, Currency.ARS)).isEmpty()
    }
}