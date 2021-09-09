package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
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
        bankAccounts.create(juanPerez, currency = "ARS")

        val davidGomez = customers.save(Customer("David Gomez"))
        bankAccounts.create(davidGomez, currency = "ARS")

        val deposit = Deposit(customers, bankAccounts)
        deposit.execute(customerName = "Juan Perez", currency = "ARS", amountToDeposit = 100);

        val transfer = Transfer(customers, bankAccounts)
        transfer.execute(debitCustomerName = "Juan Perez", currency = "ARS", amountToTransfer = 100, creditCustomerName = "David Gomez");

        val debitAccount = bankAccounts.accountOwnedBy(juanPerez, "ARS").get()
        assertThat(debitAccount.balance).isEqualTo(Balance("ARS", 0))

        val creditAccount = bankAccounts.accountOwnedBy(davidGomez, "ARS").get()
        assertThat(creditAccount.balance).isEqualTo(Balance("ARS", 100))
    }
}