package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.Customer
import javax.persistence.*

@Entity
class JpaCustomer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @Column
    val name: String
) {

    companion object {
        fun from(customer: Customer): JpaCustomer {
            return JpaCustomer(0, customer.name)
        }
    }
}
