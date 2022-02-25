package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.TestObjects.mabel
import ar.com.flow.bankaccount.domain.TestObjects.minusDollars100Limit
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.InsufficientFundsException
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import ar.com.flow.money.TestMoney.dollars110
import ar.com.flow.money.TestMoney.dollars300
import assertk.assertThat
import assertk.assertions.hasClass
import assertk.assertions.isFailure
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CheckingAccountTest {
    private val bankAccounts: BankAccounts = InMemoryBankAccounts()

    private lateinit var danielsAccount: BankAccount
    private lateinit var mabelsAccount: BankAccount

    @BeforeEach
    fun setUp() {
        danielsAccount = bankAccounts.createCheckingAccount(daniel, Currency.USD, minusDollars100Limit)
        danielsAccount.deposit(dollars100)
        mabelsAccount = bankAccounts.createCheckingAccount(mabel, Currency.USD, minusDollars100Limit)
        mabelsAccount.deposit(dollars100)
    }

    @Test
    fun withdrawalDecrementsFunds() {
        danielsAccount.withdraw(dollars10)

        assertThat(danielsAccount).decreasedFunds(dollars10)
    }

    @Test
    fun withdrawAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        danielsAccount.withdraw(dollars110)

        assertThat(danielsAccount).hasNegativeBalance(dollars10)
    }

    @Test
    fun withdrawalAmountCanNotExceedWithdrawalLimit() {
        assertThat { danielsAccount.withdraw(dollars300) }
            .isFailure()
            .hasClass(InsufficientFundsException::class)

        assertThat(danielsAccount).keepsInitialBalance()
    }

    @Test
    fun transferAmountLessThanAvailableFunds() {
        danielsAccount.transfer(dollars10, mabelsAccount)

        assertThat(danielsAccount).decreasedFunds(dollars10)
        assertThat(mabelsAccount).increasedFunds(dollars10)
    }

    @Test
    fun transferAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        danielsAccount.transfer(dollars110, mabelsAccount)

        assertThat(danielsAccount).decreasedFunds(dollars110)
        assertThat(mabelsAccount).increasedFunds(dollars110)
    }

    @Test
    fun transferAmountCanNotExceedWithdrawalLimit() {
        assertThat { danielsAccount.transfer(dollars300, mabelsAccount) }
            .isFailure()
            .hasClass(InsufficientFundsException::class)

        assertThat(danielsAccount).keepsInitialBalance()
        assertThat(mabelsAccount).keepsInitialBalance()
    }
}