package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.FundsMovement
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Receipt(
    @Id
    @GeneratedValue
    val id: Long,
    @ManyToOne
    val customer: Customer,
    @Column
    val dateTime: LocalDateTime,
    @Enumerated
    val movement: FundsMovement,
    @Enumerated
    val action: Action,
    @Column
    val currency: String,
    @Column
    val amount: Int
)


