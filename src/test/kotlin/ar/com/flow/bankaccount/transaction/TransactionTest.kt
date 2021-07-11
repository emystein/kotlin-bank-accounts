package ar.com.flow.bankaccount.transaction

import ar.com.flow.bankaccount.BankAccount
import ar.com.flow.bankaccount.BankAccountAssert.Companion.assertThat
import ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor
import ar.com.flow.bankaccount.TestObjects.francisco
import ar.com.flow.bankaccount.transaction.receipt.Action
import ar.com.flow.bankaccount.transaction.receipt.DebitPrint
import ar.com.flow.bankaccount.transaction.receipt.DebitScratch
import ar.com.flow.bankaccount.transaction.steps.Step
import ar.com.flow.money.TestObjects.dollars10
import ar.com.flow.money.TestObjects.dollars100
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionTest {
    private lateinit var debitAccount: BankAccount
    private lateinit var debit: Step

    @BeforeEach
    fun setUp() {
        debitAccount = createSavingsAccountFor(francisco, dollars100)
        debit = Step(
            debitAccount,
            DebitPrint(debitAccount, Action.Transfer),
            DebitScratch(debitAccount, Action.Transfer)
        )
    }

    @Test
    fun rollbackAfterFirstStep() {
        val creditMock = mockk<Step>()

        every { creditMock.execute(dollars10) } throws RuntimeException()

        Builder()
            .steps(debit, creditMock)
            .amount(dollars10)
            .build()
            .execute()

        assertThat(debitAccount).keepsInitialBalance()
    }
}
