package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer

interface Customers {
    fun save(customer: Customer): Customer

    @Throws(CustomerNotFound::class)
    fun customerNamed(name: String): Customer
}
