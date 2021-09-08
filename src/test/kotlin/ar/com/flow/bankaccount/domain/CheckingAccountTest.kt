package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.domain.TestObjects.createCheckingAccountFor
import ar.com.flow.bankaccount.domain.TestObjects.francisco
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
    private lateinit var franciscosAccount: BankAccount
    private lateinit var mabelsAccount: BankAccount

    @BeforeEach
    fun setUp() {
        franciscosAccount = createCheckingAccountFor(francisco, dollars100, minusDollars100Limit )
        mabelsAccount = createCheckingAccountFor(mabel, dollars100, minusDollars100Limit)
    }

    @Test
    fun withdrawalDecrementsFunds() {
        franciscosAccount.withdraw(dollars10)

        BankAccountAssert.assertThat(franciscosAccount)
            .decreasedFunds(dollars10)
    }

    @Test
    fun withdrawAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        franciscosAccount.withdraw(dollars110)

        BankAccountAssert.assertThat(franciscosAccount)
            .hasNegativeBalance(dollars10)
    }

    @Test
    fun withdrawalAmountCanNotExceedWithdrawalLimit() {
        Assertions.assertThrows(InsufficientFundsException::class.java) { franciscosAccount.withdraw(dollars300) }

        BankAccountAssert.assertThat(franciscosAccount)
            .keepsInitialBalance()
    }

    @Test
    fun transferAmountLessThanAvailableFunds() {
        franciscosAccount.transfer(dollars10, mabelsAccount)

        BankAccountAssert.assertThat(franciscosAccount)
            .decreasedFunds(dollars10)

        BankAccountAssert.assertThat(mabelsAccount)
            .increasedFunds(dollars10)
    }

    @Test
    fun transferAmountGreaterThanPositiveBalanceAndAboveWithdrawalLimit() {
        franciscosAccount.transfer(dollars110, mabelsAccount)

        BankAccountAssert.assertThat(franciscosAccount)
            .decreasedFunds(dollars110)

        BankAccountAssert.assertThat(mabelsAccount)
            .increasedFunds(dollars110)
    }

    @Test
    fun transferAmountCanNotExceedWithdrawalLimit() {
        Assertions.assertThrows(InsufficientFundsException::class.java) {
            franciscosAccount.transfer(dollars300, mabelsAccount)
        }

        BankAccountAssert.assertThat(franciscosAccount)
            .keepsInitialBalance()

        BankAccountAssert.assertThat(mabelsAccount)
            .keepsInitialBalance()
    }
}