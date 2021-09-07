package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.ports.out.CustomerNotFound
import ar.com.flow.bankaccount.ports.out.Customers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
class JpaCustomers(@Autowired private val repository: CustomerRepository) : Customers {
    private val customerMapper: CustomerMapper = CustomerMapper()

    override fun save(customer: Customer): Customer {
        val jpaCustomer = customerMapper.toJpa(customer)
        val savedJpaCustomer = repository.save(jpaCustomer)
        repository.flush()
        return customerMapper.toDomain(savedJpaCustomer)
    }

    override fun customerNamed(name: String): Customer {
        val maybeCustomer = repository.findByName(name)

        if (maybeCustomer.isPresent) {
            return customerMapper.toDomain(maybeCustomer.get())
        } else {
            throw CustomerNotFound("Customer not found: $name")
        }
    }
}
