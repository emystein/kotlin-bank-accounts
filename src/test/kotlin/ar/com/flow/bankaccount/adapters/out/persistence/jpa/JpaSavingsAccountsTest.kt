package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
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
class JpaSavingsAccountsTest {
    @Autowired
    private lateinit var savingsAccounts: SavingsAccounts

    @Test
    fun createSavingsAccount() {
        val owner = Customer("Juan Perez")

        val account = savingsAccounts.create(owner, "ARS")

        assertThat(savingsAccounts.accountOwnedBy(owner, "ARS")).hasValue(account)
    }

    @Test
    fun updateSavingsAccount() {
        val owner = Customer("Juan Perez")
        val account = savingsAccounts.create(owner, "ARS")
        account.deposit(ars100)

        savingsAccounts.save(account)

        val updatedAccount = savingsAccounts.accountOwnedBy(owner, "ARS").get()
        assertThat(updatedAccount.balance).isEqualTo(Balance.positive(ars100))
    }

    @Test
    fun containsCreatedAccount() {
        val owner = Customer("Juan Perez")

        val account = savingsAccounts.create(owner, "ARS")

        assertThat(savingsAccounts.contains(account)).isTrue
    }

    @Test
    fun transientAccountNotFound() {
        val owner = Customer("Juan Perez")
        assertThat(savingsAccounts.accountOwnedBy(owner, "ARS")).isEmpty
    }
}