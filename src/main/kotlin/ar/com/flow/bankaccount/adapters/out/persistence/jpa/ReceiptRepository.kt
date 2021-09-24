package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptRepository: JpaRepository<Receipt, Long> {
    fun findAllByAccountId(accountId: String): List<Receipt>
}
