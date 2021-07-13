package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import javax.persistence.*

@Entity
class JpaReceipt (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @ManyToOne
    val bankAccount: JpaBankAccount
    ) {

    companion object {
        fun from(receipt: Receipt): JpaReceipt {
            val jpaBankAccount = JpaBankAccount.from(receipt.destinationAccount)

            return JpaReceipt(0, jpaBankAccount)
        }
    }
}
