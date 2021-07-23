package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Customer(
    @Id
    @GeneratedValue
    val id: Long,
    @Column
    val name: String
)

