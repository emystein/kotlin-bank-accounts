package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.TestObjects.daniel
import ar.com.flow.bankaccount.ports.out.Customers
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountPersistenceConfiguration::class])
class CustomersTest {
    @Autowired
    private lateinit var customers: Customers

    @Test
    fun createCustomer() {
        val customer = customers.save(daniel)

        assertThat(customers.customerNamed(daniel.name)).isEqualTo(customer)
    }
}