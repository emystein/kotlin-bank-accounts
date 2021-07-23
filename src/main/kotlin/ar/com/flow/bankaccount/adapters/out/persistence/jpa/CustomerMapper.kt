package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.Customer

class CustomerMapper {
    fun toDomain(customer: ar.com.flow.bankaccount.adapters.out.persistence.jpa.Customer): Customer {
        return Customer(customer.name)
    }

    fun toJpa(customer: Customer): ar.com.flow.bankaccount.adapters.out.persistence.jpa.Customer {
        return Customer(0, customer.name)
    }
}
