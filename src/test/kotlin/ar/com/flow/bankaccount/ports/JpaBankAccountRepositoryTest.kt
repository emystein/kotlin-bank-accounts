package ar.com.flow.bankaccount.ports

import ar.com.flow.bankaccount.adapters.jpa.BankAccountRepository
import ar.com.flow.bankaccount.adapters.jpa.JpaBankAccount
import ar.com.flow.bankaccount.application.spring.BankAccountConfiguration
import ar.com.flow.bankaccount.domain.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.domain.TestObjects.francisco
import ar.com.flow.money.TestObjects.dollars100
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountConfiguration::class])
class JpaBankAccountRepositoryTest {
    @Autowired
    private lateinit var accounts: BankAccountRepository

    @Test
    internal fun create() {
        val franciscosAccount = createSavingsAccountFor(francisco, dollars100)

        val jpaBankAccount = JpaBankAccount.from(franciscosAccount)

        val createdAccount = accounts.save(jpaBankAccount)

        assertThat(createdAccount.id).isNotNull
        assertThat(createdAccount.owner.name).isEqualTo(francisco.name)
        assertThat(createdAccount.currency).isEqualTo(franciscosAccount.currency)
    }
}