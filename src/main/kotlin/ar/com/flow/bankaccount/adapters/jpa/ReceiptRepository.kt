package ar.com.flow.bankaccount.adapters.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptRepository: CrudRepository<JpaReceipt, Long> {

}
