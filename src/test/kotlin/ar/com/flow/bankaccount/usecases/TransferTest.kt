package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransferTest {
    private lateinit var customers: Customers
    private lateinit var bankAccounts: BankAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        bankAccounts = InMemoryBankAccounts()
    }

    @Test
    internal fun transferBetweenDifferentAccountsSameCurrency() {
        val juanPerez = customers.save(Customer("Juan Perez"))
        bankAccounts.create(juanPerez, currency = Currency.ARS)

        val davidGomez = customers.save(Customer("David Gomez"))
        bankAccounts.create(davidGomez, currency = Currency.ARS)

        val deposit = Deposit(customers, bankAccounts)
        deposit.execute(customerName = "Juan Perez", currencyCode = Currency.ARS.code, amountToDeposit = 100);

        val transfer = Transfer(customers, bankAccounts)
        transfer.execute(debitCustomerName = "Juan Perez", Currency.ARS.code, amountToTransfer = 100, creditCustomerName = "David Gomez");

        val debitAccount = bankAccounts.accountOwnedBy(juanPerez, Currency.ARS).get()
        assertThat(debitAccount.balance).isEqualTo(Balance(Currency.ARS, 0))

        val creditAccount = bankAccounts.accountOwnedBy(davidGomez, Currency.ARS).get()
        assertThat(creditAccount.balance).isEqualTo(Balance(Currency.ARS, 100))
    }
}