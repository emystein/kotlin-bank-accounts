package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import javax.persistence.*

@Entity
class BankAccount(
    @Id
    @GeneratedValue
    val id: Long,
    @ManyToOne(cascade=[CascadeType.ALL])
    val owner: Customer,
    @Column
    val currency: String
)
