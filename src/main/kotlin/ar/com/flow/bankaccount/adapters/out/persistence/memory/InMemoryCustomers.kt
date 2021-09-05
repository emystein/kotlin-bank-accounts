package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.Customer
import ar.com.flow.bankaccount.ports.out.CustomerNotFound
import ar.com.flow.bankaccount.ports.out.Customers

class InMemoryCustomers : Customers {
    private val customers: MutableList<Customer> = mutableListOf()

    override fun save(customer: Customer): Customer {
        customers.add(customer)
        return customer
    }

    override fun customerNamed(name: String): Customer {
        return customers.find { customer -> customer.name == name }
            ?: throw CustomerNotFound("Customer not found: $name")
    }
}
