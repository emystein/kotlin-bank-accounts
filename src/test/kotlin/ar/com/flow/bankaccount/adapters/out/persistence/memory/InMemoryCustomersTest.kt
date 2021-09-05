package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.ports.out.CustomerNotFound
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class InMemoryCustomersTest {
    @Test
    internal fun throwExceptionOnCustomerNotFound() {
        val customers = InMemoryCustomers()

        Assertions.assertThrows(CustomerNotFound::class.java) {
            customers.customerNamed("Not found")
        }
    }
}