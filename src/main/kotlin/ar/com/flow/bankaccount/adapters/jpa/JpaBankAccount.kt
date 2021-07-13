package ar.com.flow.bankaccount.adapters.jpa

import ar.com.flow.bankaccount.domain.BankAccount
import javax.persistence.*

@Entity
class JpaBankAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    @ManyToOne
    val owner: JpaCustomer
) {

    companion object {
        fun from(bankAccount: BankAccount): JpaBankAccount {
            return JpaBankAccount(0, JpaCustomer.from(bankAccount.owner))
        }
    }
}
