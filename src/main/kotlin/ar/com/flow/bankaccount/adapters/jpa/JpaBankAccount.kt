package ar.com.flow.bankaccount.adapters.jpa

import javax.persistence.*

@Entity
class JpaBankAccount(
    @Id
    @GeneratedValue
    val id: Long,
    @ManyToOne(cascade=[CascadeType.ALL])
    val owner: JpaCustomer,
    @Column
    val currency: String
)
