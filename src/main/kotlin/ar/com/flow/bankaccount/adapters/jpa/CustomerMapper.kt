package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.Customer

class CustomerMapper {
    fun toDomain(jpaCustomer: JpaCustomer): Customer {
        return Customer(jpaCustomer.name)
    }

    fun toJpa(customer: Customer): JpaCustomer {
        return JpaCustomer(0, customer.name)
    }
}
