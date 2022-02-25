package ar.com.flow.bankaccount.domain.transaction

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.domain.BankAccount
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.keepsInitialBalance
import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.DebitPrint
import ar.com.flow.bankaccount.domain.transaction.receipt.DebitScratch
import ar.com.flow.bankaccount.domain.transaction.steps.Step
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import assertk.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionTest {
    private val bankAccounts: BankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

    private lateinit var debitAccount: BankAccount
    private lateinit var debit: Step

    @BeforeEach
    fun setUp() {
        debitAccount = bankAccounts.createSavingsAccount(daniel, Currency.USD)
        debitAccount.deposit(dollars100)

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