package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountPersistenceConfiguration::class])
class JpaSavingsAccountsTest {
    @Autowired
    private lateinit var jpaSavingsAccounts: SavingsAccounts

    @Test
    fun createSavingsAccount() {
        val owner = Customer("Juan Perez")

        val account = jpaSavingsAccounts.create(owner, "ARS")

        assertThat(jpaSavingsAccounts.accountOwnedBy(owner, "ARS")).isEqualTo(account)
    }

    @Test
    fun containsCreatedAccount() {
        val owner = Customer("Juan Perez")

        val account = jpaSavingsAccounts.create(owner, "ARS")

        assertThat(jpaSavingsAccounts.contains(account)).isTrue
    }
}