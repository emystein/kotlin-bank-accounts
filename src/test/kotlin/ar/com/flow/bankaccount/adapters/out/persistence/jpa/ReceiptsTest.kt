package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.domain.transaction.receipt.Action.Deposit
import ar.com.flow.bankaccount.domain.transaction.receipt.FundsMovement.Credit
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.Receipts
import ar.com.flow.money.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import java.time.LocalDateTime.now

@DataJpaTest
@ContextConfiguration(classes = [BankAccountPersistenceConfiguration::class])
class ReceiptsTest {
    private val dollars = "USD"

    @Autowired
    private lateinit var receipts: Receipts

    @Test
    fun addReceipt() {
        val balance = Balance.positive(Money(dollars, 100))
        val receipt = Receipt(daniel, now(), Credit, Deposit, balance, balance)

        receipts.add(receipt)

        assertThat(receipts.all(daniel, dollars)).isEqualTo(listOf(receipt))
    }
}