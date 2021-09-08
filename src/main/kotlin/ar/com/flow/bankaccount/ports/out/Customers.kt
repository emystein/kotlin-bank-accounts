package ar.com.flow.bankaccount.ports.out

import ar.com.flow.Customer
import java.util.*

interface Customers {
    fun save(customer: Customer): Customer

    fun customerNamed(name: String): Optional<Customer>
}
