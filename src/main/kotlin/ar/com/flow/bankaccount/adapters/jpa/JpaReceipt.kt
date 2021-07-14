package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.bankaccount.domain.transaction.receipt.Action
import ar.com.flow.bankaccount.domain.transaction.receipt.Receipt
import javax.persistence.*

@Entity
class JpaReceipt(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @ManyToOne
    val bankAccount: JpaBankAccount,
    @Enumerated
    val action: Action,
    @Column
    val currency: String,
    @Column
    val amount: Int,
    @Column
    val resultAmount: Int
) {

    // TODO Extract class
    companion object {
        fun from(receipt: Receipt): JpaReceipt {
            val jpaBankAccount = JpaBankAccount.from(receipt.destinationAccount)

            return JpaReceipt(
                0,
                jpaBankAccount,
                receipt.action,
                receipt.amount.currency,
                receipt.amount.amount,
                receipt.resultBalance.amount
            )
        }
    }
}
