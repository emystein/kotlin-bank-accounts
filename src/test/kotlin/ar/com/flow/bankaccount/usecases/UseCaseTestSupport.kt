package ar.com.flow.bankaccount.usecases

import ar.com.flow.Customer
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryBankAccounts
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.domain.Currency
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import org.junit.jupiter.api.BeforeEach

abstract class UseCaseTestSupport {
    lateinit var customers: Customers
    lateinit var bankAccounts: BankAccounts
    lateinit var juanPerez: Customer
    lateinit var davidGomez: Customer

    @BeforeEach
    open fun setUp() {
        customers = InMemoryCustomers()
        bankAccounts = InMemoryBankAccounts()
        juanPerez = customers.save(Customer(name = "Juan Perez"))
        bankAccounts.create(juanPerez, currency = Currency.ARS)
        davidGomez = customers.save(Customer(name = "David Gomez"))
        bankAccounts.create(davidGomez, currency = Currency.ARS)
    }
}