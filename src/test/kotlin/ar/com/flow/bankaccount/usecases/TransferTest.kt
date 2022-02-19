package ar.com.flow.bankaccount.usecases

import ar.com.flow.bankaccount.domain.Balance
import ar.com.flow.bankaccount.domain.Currency
import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransferTest: UseCaseTestSupport() {
    private lateinit var deposit: Deposit
    private lateinit var transfer: Transfer

    @BeforeEach
    override fun setUp() {
        super.setUp()
        deposit = Deposit(customers, bankAccounts)
        transfer = Transfer(customers, bankAccounts)
    }

    @Test
    internal fun transferBetweenDifferentAccountsSameCurrency() {
        deposit.execute(customerName = "Juan Perez", currencyCode = Currency.ARS.code, amountToDeposit = 100)

        transfer.execute(debitCustomerName = "Juan Perez", Currency.ARS.code, amountToTransfer = 100, creditCustomerName = "David Gomez")

        val debitAccount = bankAccounts.ownedBy(juanPerez, Currency.ARS).first()
        assertThat(debitAccount.balance).isEqualTo(Balance(Currency.ARS, 0))

        val creditAccount = bankAccounts.ownedBy(davidGomez, Currency.ARS).first()
        assertThat(creditAccount.balance).isEqualTo(Balance(Currency.ARS, 100))
    }
}