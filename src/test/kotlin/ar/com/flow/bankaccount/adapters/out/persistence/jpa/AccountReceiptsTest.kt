package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.application.spring.BankAccountApplication
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.DifferentAccountException
import ar.com.flow.bankaccount.domain.TestObjects
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.TestMoney
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isFailure
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [BankAccountApplication::class])
internal class AccountReceiptsTest {
    @Autowired
    private lateinit var bankAccounts: BankAccounts
    @Autowired
    private lateinit var receiptMapper: ReceiptMapper
    @Autowired
    private lateinit var receiptRepository: ReceiptRepository

    private lateinit var danielsAccount: BankAccount

    private lateinit var mabelsAccount: BankAccount

    @BeforeEach
    fun setUp() {
        receiptRepository.deleteAll()
        danielsAccount = bankAccounts.createSavingsAccount(TestObjects.daniel, Currency.USD)
        mabelsAccount = bankAccounts.createSavingsAccount(TestObjects.mabel, Currency.USD)
    }

    @Test
    fun shouldNotAddReceiptFromDifferentAccount() {
        val mabelReceipt = Receipt.creditDeposit(mabelsAccount, TestMoney.dollars10)

        val danielsReceipts = AccountReceipts(danielsAccount.id, receiptMapper, receiptRepository)

        assertThat { danielsReceipts.add(mabelReceipt) }
            .isFailure()
            .hasClass(DifferentAccountException::class)
    }
}