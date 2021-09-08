package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.ports.out.Customers
import java.util.*

class InMemoryCustomers : Customers {
    private val customers: MutableList<Customer> = mutableListOf()

    override fun save(customer: Customer): Customer {
        customers.add(customer)
        return customer
    }

    override fun customerNamed(name: String): Optional<Customer> {
        return Optional.ofNullable(customers.find { customer -> customer.name == name })
    }
}
