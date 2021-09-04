package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.domain.balance.Balance
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DepositTest {
    private lateinit var customers: Customers
    private lateinit var bankAccounts: BankAccounts

    @BeforeEach
    internal fun setUp() {
        customers = InMemoryCustomers()
        bankAccounts = InMemoryBankAccounts()
    }

    @Test
    internal fun deposit() {
        val juanPerez = customers.save(Customer("Juan Perez"))
        bankAccounts.create(juanPerez, currency = "ARS")

        val deposit = Deposit(customers, bankAccounts)

        deposit.deposit(customerName = "Juan Perez", currency = "ARS", amountToDeposit = 100);

        val savingsAccount = bankAccounts.accountOwnedBy(juanPerez, "ARS")!!
        assertThat(savingsAccount.balance).isEqualTo(Balance("ARS", 100));
    }

    @Test
    internal fun rejectDepositForNonCustomer() {
        val deposit = Deposit(customers, bankAccounts)

        Assertions.assertThrows(CustomerNotFound::class.java) {
            deposit.deposit("Customer not found", "ARS", 100);
        }
    }

    @Test
    internal fun rejectDepositForCurrencyNotUsed() {
        val juanPerez = customers.save(Customer("Juan Perez"))
        bankAccounts.create(juanPerez, "ARS")

        val deposit = Deposit(customers, bankAccounts)

        Assertions.assertThrows(CurrencyNotUsed::class.java) {
            deposit.deposit("Juan Perez", "BLA", 100);
        }
    }
}