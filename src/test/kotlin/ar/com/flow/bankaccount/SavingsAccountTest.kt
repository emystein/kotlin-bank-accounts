package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.balance.Balance.Companion.zero
import ar.com.flow.bankaccount.transfer.SameAccountException
import ar.com.flow.money.InsufficientFundsException
import ar.com.flow.money.TestObjects.dollars10
import ar.com.flow.money.TestObjects.dollars200
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SavingsAccountTest {
    private lateinit var franciscosAccount: BankAccount
    private lateinit var mabelsAccount: BankAccount

    @BeforeEach
    fun setUp() {
        franciscosAccount =
            TestObjects.createSavingsAccountFor(TestObjects.francisco, ar.com.flow.money.TestObjects.dollars100)
        mabelsAccount = TestObjects.createSavingsAccountFor(TestObjects.mabel, ar.com.flow.money.TestObjects.dollars100)
    }

    @Test
    fun createdAccountHasBasicInformation() {
        val account = SavingsAccount(TestObjects.francisco, "ARS")
        assertEquals(TestObjects.francisco, account.owner)
        assertEquals("ARS", account.currency)
        assertEquals(zero("ARS"), account.balance)
    }

    @Test
    fun depositIncrementsFunds() {
        franciscosAccount.deposit(dollars10)
        BankAccountAssert.assertThat(franciscosAccount)
            .increasedFunds(dollars10)
    }

    @Test
    fun withdrawalDecrementsFunds() {
        franciscosAccount.withdraw(dollars10)
        BankAccountAssert.assertThat(franciscosAccount)
            .decreasedFunds(dollars10)
    }

    @Test
    fun canNotWithdrawAmountGreaterThanAvailableFunds() {
        Assertions.assertThrows(InsufficientFundsException::class.java) { franciscosAccount.withdraw(dollars200) }
        BankAccountAssert.assertThat(franciscosAccount).keepsInitialBalance()
    }

    @Test
    fun withdrawalAddsTransactionRecordToStatement() {
        franciscosAccount.withdraw(dollars10)
        OptionalReceiptAssert.assertThat(franciscosAccount.statement.last())
            .isPresent()
            .isWithdrawal()
            .hasNegativeBalance(dollars10)
    }

    @Test
    fun transferAmountLessThanAvailableFunds() {
        franciscosAccount.transfer(dollars10, mabelsAccount)
        BankAccountAssert.assertThat(franciscosAccount)
            .decreasedFunds(dollars10)
        BankAccountAssert.assertThat(mabelsAccount).increasedFunds(dollars10)
        OptionalReceiptAssert.assertThat(franciscosAccount.statement.last())
            .isPresent()
            .isDebit()
            .isTransfer()
            .hasNegativeBalance(dollars10)
            .hasCreditAccount(mabelsAccount)
        OptionalReceiptAssert.assertThat(mabelsAccount.statement.last())
            .isPresent()
            .isCredit()
            .isTransfer()
            .hasPositiveBalance(dollars10)
    }

    @Test
    fun canNotTransferAmountGreaterThanAvailableFunds() {
        Assertions.assertThrows(InsufficientFundsException::class.java) {
            franciscosAccount.transfer(dollars200, mabelsAccount)
        }
        BankAccountAssert.assertThat(franciscosAccount).keepsInitialBalance()
        BankAccountAssert.assertThat(mabelsAccount).keepsInitialBalance()
    }

    @Test
    fun canNotTransferToSelf() {
        Assertions.assertThrows(SameAccountException::class.java) {
            franciscosAccount.transfer(dollars10, franciscosAccount)
        }
        BankAccountAssert.assertThat(franciscosAccount).keepsInitialBalance()
    }
}