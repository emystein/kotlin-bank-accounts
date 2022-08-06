package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.domain.Balance.Companion.zero
import ar.com.flow.bankaccount.domain.Currency.ARS
import ar.com.flow.bankaccount.domain.Currency.USD
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.TestObjects.mabel
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.InsufficientFundsException
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import ar.com.flow.money.TestMoney.dollars200
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SavingsAccountTest {
    private val bankAccounts: BankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

    private lateinit var danielsAccount: BankAccount
    private lateinit var mabelsAccount: BankAccount

    @BeforeEach
    fun setUp() {
        danielsAccount = bankAccounts.createSavingsAccount(daniel, USD)
        danielsAccount.deposit(dollars100)
        mabelsAccount = bankAccounts.createSavingsAccount(mabel, USD)
        mabelsAccount.deposit(dollars100)
    }

    @Test
    fun createdAccountHasBalance0() {
        val account = bankAccounts.createSavingsAccount(daniel, ARS)

        assertThat(account.balance).isEqualTo(zero(ARS))
    }

    @Test
    fun canCreateMoreThanOneAccountPerCustomerAndCurrency() {
        val account1 = bankAccounts.createSavingsAccount(daniel, ARS)
        val account2 = bankAccounts.createSavingsAccount(daniel, ARS)

        assertThat(account1.id).isNotEqualTo(account2.id)
    }


    @Test
    fun depositIncrementsFunds() {
        danielsAccount.deposit(dollars10)

        assertThat(danielsAccount).increasedFunds(dollars10)
    }

    @Test
    fun withdrawalDecrementsFunds() {
        danielsAccount.withdraw(dollars10)

        assertThat(danielsAccount).decreasedFunds(dollars10)
    }

    @Test
    fun canNotWithdrawAmountGreaterThanAvailableFunds() {
        assertThat { danielsAccount.withdraw(dollars200) }
            .isFailure()
            .hasClass(InsufficientFundsException::class)

        assertThat(danielsAccount).keepsInitialBalance()
    }

    @Test
    fun withdrawalAddsTransactionRecordToStatement() {
        danielsAccount.withdraw(dollars10)

        val actual = danielsAccount.statement.last().get()
        assertThat(actual).isWithdrawal()
        assertThat(actual).hasNegativeBalance(dollars10)
    }

    @Test
    fun transferAmountLessThanAvailableFunds() {
        danielsAccount.transfer(dollars10, mabelsAccount)

        assertThat(danielsAccount).decreasedFunds(dollars10)
        assertThat(mabelsAccount).increasedFunds(dollars10)

        val danielsLastMovement = danielsAccount.statement.last().get()
        assertThat(danielsLastMovement).isDebit()
        assertThat(danielsLastMovement).isTransfer()
        assertThat(danielsLastMovement).hasNegativeBalance(dollars10)
        assertThat(danielsLastMovement).hasCreditAccount(mabelsAccount)

        val mabelsLastMovement = mabelsAccount.statement.last().get()
        assertThat(mabelsLastMovement).isCredit()
        assertThat(mabelsLastMovement).isTransfer()
        assertThat(mabelsLastMovement).hasPositiveBalance(dollars10)
    }

    @Test
    fun canNotTransferAmountGreaterThanAvailableFunds() {
        assertThat { danielsAccount.transfer(dollars200, mabelsAccount) }
            .isFailure()
            .hasClass(InsufficientFundsException::class)

        assertThat(danielsAccount).keepsInitialBalance()
        assertThat(mabelsAccount).keepsInitialBalance()
    }

    @Test
    fun canNotTransferToSelf() {
        assertThat { danielsAccount.transfer(dollars10, danielsAccount) }
            .isFailure()
            .hasClass(SameAccountException::class)

        assertThat(danielsAccount).keepsInitialBalance()
    }

    @Test
    fun canCreateMultipleAccountsForSameCustomerAndCurrency() {
        val danielsAccount1 = bankAccounts.createSavingsAccount(daniel, ARS)
        val danielsAccount2 = bankAccounts.createSavingsAccount(daniel, ARS)

        assertThat(danielsAccount1).isNotEqualTo(danielsAccount2)
    }

    @Test
    internal fun retrieveAccountByItsId() {
        val retrievedAccount = bankAccounts.getById(danielsAccount.id)

        assertThat(retrievedAccount).isPresent()
        assertThat(retrievedAccount.get()).isEqualTo(danielsAccount)
    }
}