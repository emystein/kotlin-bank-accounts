package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.TestObjects.francisco
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
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
    private lateinit var savingsAccounts: SavingsAccounts

    val pesos = "ARS"

    @Test
    fun createSavingsAccount() {
        val account = savingsAccounts.create(francisco, pesos)

        assertThat(savingsAccounts.accountOwnedBy(francisco, pesos)).hasValue(account)
    }

    @Test
    fun updateSavingsAccount() {
        val account = savingsAccounts.create(francisco, pesos)
        account.deposit(ars100)

        savingsAccounts.save(account)

        val updatedAccount = savingsAccounts.accountOwnedBy(francisco, pesos).get()
        assertThat(updatedAccount.balance).isEqualTo(Balance.positive(ars100))
    }

    @Test
    fun containsCreatedAccount() {
        val account = savingsAccounts.create(francisco, pesos)

        assertThat(savingsAccounts.contains(account)).isTrue
    }

    @Test
    fun transientAccountNotFound() {
        assertThat(savingsAccounts.accountOwnedBy(francisco, pesos)).isEmpty
    }
}