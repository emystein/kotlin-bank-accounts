package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.application.spring.BankAccountApplication
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.DifferentAccountException
import ar.com.flow.bankaccount.domain.SavingsAccount
import ar.com.flow.bankaccount.domain.TestObjects
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.money.TestMoney
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [BankAccountApplication::class])
internal class AccountReceiptsTest {
    @Autowired
    private lateinit var accountRegistry: BankAccountRegistry
    @Autowired
    private lateinit var receiptMapper: ReceiptMapper
    @Autowired
    private lateinit var receiptRepository: ReceiptRepository

    private lateinit var danielsAccount: SavingsAccount

    private lateinit var mabelsAccount: SavingsAccount

    @BeforeEach
    fun setUp() {
        receiptRepository.deleteAll()
        danielsAccount = accountRegistry.createSavingsAccount(TestObjects.daniel, Currency.USD)
        mabelsAccount = accountRegistry.createSavingsAccount(TestObjects.mabel, Currency.USD)
    }

    @Test
    fun shouldNotAddReceiptFromDifferentAccount() {
        val mabelReceipt = Receipt.credit(mabelsAccount, Action.Deposit, TestMoney.dollars10)

        val danielsReceipts = AccountReceipts(danielsAccount.id, receiptMapper, receiptRepository)

        assertThrows(DifferentAccountException::class.java) { danielsReceipts.add(mabelReceipt) }
    }
}