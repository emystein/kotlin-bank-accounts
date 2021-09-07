package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemorySavingsAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransferTest {
    private lateinit var customers: Customers
    private lateinit var savingsAccounts: SavingsAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        savingsAccounts = InMemorySavingsAccounts()
    }

    @Test
    internal fun transferBetweenDifferentAccountsSameCurrency() {
        val juanPerez = customers.save(Customer("Juan Perez"))
        savingsAccounts.create(juanPerez, currency = "ARS")

        val davidGomez = customers.save(Customer("David Gomez"))
        savingsAccounts.create(davidGomez, currency = "ARS")

        val deposit = Deposit(customers, savingsAccounts)
        deposit.execute(customerName = "Juan Perez", currency = "ARS", amountToDeposit = 100);

        val transfer = Transfer(customers, savingsAccounts)
        transfer.execute(debitCustomerName = "Juan Perez", currency = "ARS", amountToTransfer = 100, creditCustomerName = "David Gomez");

        val debitAccount = savingsAccounts.accountOwnedBy(juanPerez, "ARS")
        assertThat(debitAccount.balance).isEqualTo(Balance("ARS", 0));

        val creditAccount = savingsAccounts.accountOwnedBy(davidGomez, "ARS")
        assertThat(creditAccount.balance).isEqualTo(Balance("ARS", 100));
    }
}