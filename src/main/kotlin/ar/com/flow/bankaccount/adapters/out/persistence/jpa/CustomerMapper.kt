package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CustomerMapper(@Autowired private val customerRepository: CustomerRepository) {
    fun toDomain(customer: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Customer): Customer {
        return Customer(customer.name)
    }

    fun toJpa(customer: Customer): ar.com.flow.bankaccount.adapters.out.persistence.jpa.Customer {
        val maybeJpaCustomer = customerRepository.findByName(customer.name)

        return if (maybeJpaCustomer.isPresent) {
            maybeJpaCustomer.get()
        } else {
            customerRepository.save(Customer(0, customer.name))
        }
    }
}
