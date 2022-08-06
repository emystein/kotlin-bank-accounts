package ar.com.flow.bankaccount.domain

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.domain.Currency.USD
import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.domain.TestObjects.mabel
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.money.Dollars
import ar.com.flow.money.TestMoney.dollars100
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class InterBankTransferTest {
    private val bbvaBankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())
    private val santanderBankAccounts = InMemoryBankAccounts(UUIDAccountIdGenerator())

    private lateinit var danielsBbvaUsdAccount: BankAccount
    private lateinit var mabelsSantanderUsdAccount: BankAccount

    @BeforeEach
    fun setUp() {
        danielsBbvaUsdAccount = bbvaBankAccounts.createSavingsAccount(daniel, USD)
        danielsBbvaUsdAccount.deposit(dollars100)
        mabelsSantanderUsdAccount = santanderBankAccounts.createSavingsAccount(mabel, USD)
        mabelsSantanderUsdAccount.deposit(dollars100)
    }

    @Test
    fun transferBetweenBanks() {
        val originalMabelsBalance = mabelsSantanderUsdAccount.balance

        danielsBbvaUsdAccount.transfer(Dollars.amount(10), mabelsSantanderUsdAccount)

        assertThat(mabelsSantanderUsdAccount.balance).isEqualTo(originalMabelsBalance.plus(Dollars.amount(10)))
    }
}