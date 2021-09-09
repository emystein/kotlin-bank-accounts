package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer
import ar.com.flow.bankaccount.ports.out.Customers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*
import javax.transaction.Transactional

@Component
@Transactional
class Customers(@Autowired private val repository: CustomerRepository) :
    Customers {
    private val customerMapper: CustomerMapper = CustomerMapper(repository)

    override fun save(customer: Customer): Customer {
        val jpaCustomer = customerMapper.toJpa(customer)
        val savedJpaCustomer = repository.save(jpaCustomer)
        repository.flush()
        return customerMapper.toDomain(savedJpaCustomer)
    }

    override fun customerNamed(name: String): Optional<Customer> {
        val maybeCustomer = repository.findByName(name)
        return maybeCustomer.map { customerMapper.toDomain(it) }
    }
}
