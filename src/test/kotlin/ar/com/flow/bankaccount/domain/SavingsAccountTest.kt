package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryAccountRegistry
import ar.com.flow.bankaccount.domain.Balance.Companion.zero
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.TestObjects.mabel
import ar.com.flow.bankaccount.domain.transfer.SameAccountException
import ar.com.flow.money.InsufficientFundsException
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import ar.com.flow.money.TestMoney.dollars200
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SavingsAccountTest {
    private val accountRegistry = InMemoryAccountRegistry()
    
    private lateinit var danielsAccount: BankAccount
    private lateinit var mabelsAccount: BankAccount

    @BeforeEach
    fun setUp() {
        danielsAccount = accountRegistry.createSavingsAccount(daniel, dollars100)
        mabelsAccount = accountRegistry.createSavingsAccount(mabel, dollars100)
    }

    @Test
    fun createdAccountHasBalance0() {
        val account = accountRegistry.createSavingsAccount(daniel, Currency.ARS)

        assertEquals(zero(Currency.ARS), account.balance)
    }

    @Test
    fun depositIncrementsFunds() {
        danielsAccount.deposit(dollars10)

        BankAccountAssert.assertThat(danielsAccount)
            .increasedFunds(dollars10)
    }

    @Test
    fun withdrawalDecrementsFunds() {
        danielsAccount.withdraw(dollars10)

        BankAccountAssert.assertThat(danielsAccount)
            .decreasedFunds(dollars10)
    }

    @Test
    fun canNotWithdrawAmountGreaterThanAvailableFunds() {
        assertThrows(InsufficientFundsException::class.java) { danielsAccount.withdraw(dollars200) }

        BankAccountAssert.assertThat(danielsAccount).keepsInitialBalance()
    }

    @Test
    fun withdrawalAddsTransactionRecordToStatement() {
        danielsAccount.withdraw(dollars10)

        OptionalReceiptAssert.assertThat(danielsAccount.statement.last())
            .isPresent()
            .isWithdrawal()
            .hasNegativeBalance(dollars10)
    }

    @Test
    fun transferAmountLessThanAvailableFunds() {
        danielsAccount.transfer(dollars10, mabelsAccount)

        BankAccountAssert.assertThat(danielsAccount)
            .decreasedFunds(dollars10)

        BankAccountAssert.assertThat(mabelsAccount).increasedFunds(dollars10)

        OptionalReceiptAssert.assertThat(danielsAccount.statement.last())
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
        assertThrows(InsufficientFundsException::class.java) {
            danielsAccount.transfer(dollars200, mabelsAccount)
        }
        BankAccountAssert.assertThat(danielsAccount).keepsInitialBalance()
        BankAccountAssert.assertThat(mabelsAccount).keepsInitialBalance()
    }

    @Test
    fun canNotTransferToSelf() {
        assertThrows(SameAccountException::class.java) {
            danielsAccount.transfer(dollars10, danielsAccount)
        }
        BankAccountAssert.assertThat(danielsAccount).keepsInitialBalance()
    }

    @Test
    fun canCreateMultipleAccountsForSameCustomerAndCurrency() {
        val danielsAccount1 = accountRegistry.createSavingsAccount(daniel, dollars100)
        val danielsAccount2 = accountRegistry.createSavingsAccount(daniel, dollars100)

        assertThat(danielsAccount1).isNotEqualTo(danielsAccount2)
    }
}