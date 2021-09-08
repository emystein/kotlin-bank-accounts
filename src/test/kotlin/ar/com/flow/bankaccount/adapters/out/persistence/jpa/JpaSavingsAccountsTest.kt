package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.money.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountPersistenceConfiguration::class])
class JpaSavingsAccountsTest {
    val ars100 = Money("ARS", 100)

    @Autowired
    private lateinit var jpaSavingsAccounts: SavingsAccounts

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var bankAccountRepository: BankAccountRepository

    @Autowired
    private lateinit var receiptRepository: ReceiptRepository

    @Autowired
    private lateinit var accountMapper: SavingsAccountMapper

    @Test
    fun createSavingsAccount() {
        val owner = Customer("Juan Perez")

        val account = jpaSavingsAccounts.create(owner, "ARS")

        assertThat(jpaSavingsAccounts.accountOwnedBy(owner, "ARS")).isEqualTo(account)
    }

    @Test
    fun updateSavingsAccount() {
        val owner = Customer("Juan Perez")
        val account = jpaSavingsAccounts.create(owner, "ARS")
        account.deposit(ars100)

        jpaSavingsAccounts.save(account)

        val updatedAccount = jpaSavingsAccounts.accountOwnedBy(owner, "ARS")
        assertThat(updatedAccount.balance).isEqualTo(Balance.positive(ars100))
    }

    @Test
    fun containsCreatedAccount() {
        val owner = Customer("Juan Perez")

        val account = jpaSavingsAccounts.create(owner, "ARS")

        assertThat(jpaSavingsAccounts.contains(account)).isTrue
    }

    @Test
    fun doNotContainsTransientAccount() {
        val owner = Customer("Juan Perez")

        val receiptMapper = ReceiptMapper(accountMapper)
        val statement = Statement(owner, "ARS", customerRepository, bankAccountRepository, receiptMapper, receiptRepository)
        val account = SavingsAccount(owner, "ARS", statement)

        assertThat(jpaSavingsAccounts.contains(account)).isFalse
    }
}