package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.TestObjects.francisco
import ar.com.flow.bankaccount.balance.Balance.Companion.positive
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.Receipt.Companion.credit
import ar.com.flow.bankaccount.transaction.receipt.Receipt.Companion.debit
import ar.com.flow.money.TestObjects.dollars10
import ar.com.flow.money.TestObjects.dollars100
import ar.com.flow.money.TestObjects.dollars110
import ar.com.flow.money.TestObjects.dollars90
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ReceiptTest {
    private lateinit var franciscosAccount: BankAccount
    
    @BeforeEach
    fun setUp() {
        franciscosAccount =
            createSavingsAccountFor(francisco, dollars100)
    }

    @Test
    fun recordCreditResultBalance() {
        val receipt = credit(franciscosAccount, Action.Deposit, dollars10)

        TransactionRecordAssert.assertThat(receipt)
            .hasResultBalance(positive(dollars110))
    }

    @Test
    fun recordDebitResultBalance() {
        val receipt = debit(franciscosAccount, Action.Deposit, dollars10)

        TransactionRecordAssert.assertThat(receipt)
            .hasResultBalance(positive(dollars90))
    }
}