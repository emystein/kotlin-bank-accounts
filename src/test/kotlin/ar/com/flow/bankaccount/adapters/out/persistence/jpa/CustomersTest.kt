package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountPersistenceConfiguration::class])
class CustomersTest {
    @Autowired
    private lateinit var customers: Customers

    @Test
    fun createCustomer() {
        val customer = customers.save(Customer("Juan Perez"))

        assertThat(customers.customerNamed("Juan Perez")).isEqualTo(customer)
    }

    @Test
    fun shouldNotCreateTwoCustomersWithSameName() {
        customers.save(Customer("Juan Perez"))

        assertThrows(DataIntegrityViolationException::class.java) {
            customers.save(Customer("Juan Perez"))
        }
    }
}