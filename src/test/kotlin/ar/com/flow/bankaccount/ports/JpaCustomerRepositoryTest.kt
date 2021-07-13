package ar.com.flow.bankaccount.ports

import ar.com.flow.bankaccount.adapters.jpa.CustomerRepository
import ar.com.flow.bankaccount.adapters.jpa.JpaCustomer
import ar.com.flow.bankaccount.application.spring.BankAccountConfiguration
import ar.com.flow.bankaccount.domain.TestObjects.francisco
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration

@DataJpaTest
@ContextConfiguration(classes = [BankAccountConfiguration::class])
class JpaCustomerRepositoryTest {
    @Autowired
    private lateinit var customers: CustomerRepository

    @Test
    internal fun addCustomer() {
        val jpaCustomer = JpaCustomer.from(francisco)

        val createdJpaCustomer = customers.save(jpaCustomer)

        assertThat(customers.findById(createdJpaCustomer.id)).isNotEmpty
        assertThat(createdJpaCustomer.name).isEqualTo(francisco.name)
    }
}