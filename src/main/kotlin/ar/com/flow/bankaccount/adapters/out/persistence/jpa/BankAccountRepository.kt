package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface BankAccountRepository : JpaRepository<BankAccount, Long> {
    fun findByAccountId(value: String): Optional<BankAccount>
    fun findAllByOwnerAndCurrency(owner: Customer, currency: String): List<BankAccount>
}
