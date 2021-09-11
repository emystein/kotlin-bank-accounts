package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryAccountRegistry
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.TestObjects.mabel
import ar.com.flow.bankaccount.domain.TestObjects.minusDollars100Limit
import ar.com.flow.money.InsufficientFundsException
import ar.com.flow.money.TestMoney.dollars10
import ar.com.flow.money.TestMoney.dollars100
import ar.com.flow.money.TestMoney.dollars110
import ar.com.flow.money.TestMoney.dollars300
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CheckingAccountTest {
    private val accountRegistry = InMemoryAccountRegistry()

    private lateinit var danielsAccount: BankAccount
    private lateinit var mabelsAccount: BankAccount

    @BeforeEach
    fun setUp() {
        danielsAccount = accountRegistry.createCheckingAccountFor(daniel, dollars100, minusDollars100Limit)
        mabelsAccount = accountRegistry.createCheckingAccountFor(mabel, dollars100, minusDollars100Limit)
    }

    @Test
    fun withdrawalDecrementsFunds() {
        danielsAccount.withdraw(dollars10)

        BankAccountAssert.assertThat(danielsAccount)
            .decreasedFunds(dollars10)
    }

    @Test
    fun withdrawAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        danielsAccount.withdraw(dollars110)

        BankAccountAssert.assertThat(danielsAccount)
            .hasNegativeBalance(dollars10)
    }

    @Test
    fun withdrawalAmountCanNotExceedWithdrawalLimit() {
        Assertions.assertThrows(InsufficientFundsException::class.java) { danielsAccount.withdraw(dollars300) }

        BankAccountAssert.assertThat(danielsAccount)
            .keepsInitialBalance()
    }

    @Test
    fun transferAmountLessThanAvailableFunds() {
        danielsAccount.transfer(dollars10, mabelsAccount)

        BankAccountAssert.assertThat(danielsAccount)
            .decreasedFunds(dollars10)

        BankAccountAssert.assertThat(mabelsAccount)
            .increasedFunds(dollars10)
    }

    @Test
    fun transferAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        danielsAccount.transfer(dollars110, mabelsAccount)

        BankAccountAssert.assertThat(danielsAccount)
            .decreasedFunds(dollars110)

        BankAccountAssert.assertThat(mabelsAccount)
            .increasedFunds(dollars110)
    }

    @Test
    fun transferAmountCanNotExceedWithdrawalLimit() {
        Assertions.assertThrows(InsufficientFundsException::class.java) {
            danielsAccount.transfer(dollars300, mabelsAccount)
        }

        BankAccountAssert.assertThat(danielsAccount)
            .keepsInitialBalance()

        BankAccountAssert.assertThat(mabelsAccount)
            .keepsInitialBalance()
    }
}