package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.balance.Balance
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

    val pesos = "ARS"

    @Test
    fun createSavingsAccount() {
        val account = bankAccounts.create(daniel, pesos)

        assertThat(bankAccounts.accountOwnedBy(daniel, pesos)).hasValue(account)
    }

    @Test
    fun updateSavingsAccount() {
        val account = bankAccounts.create(daniel, pesos)
        account.deposit(ars100)

        bankAccounts.save(account)

        val updatedAccount = bankAccounts.accountOwnedBy(daniel, pesos).get()
        assertThat(updatedAccount.balance).isEqualTo(Balance.positive(ars100))
    }

    @Test
    fun containsCreatedAccount() {
        val account = bankAccounts.create(daniel, pesos)

        assertThat(bankAccounts.contains(account)).isTrue
    }

    @Test
    fun transientAccountNotFound() {
        assertThat(bankAccounts.accountOwnedBy(daniel, pesos)).isEmpty
    }
}