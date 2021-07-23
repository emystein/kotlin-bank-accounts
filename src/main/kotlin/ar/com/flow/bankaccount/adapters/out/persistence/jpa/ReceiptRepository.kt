package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ReceiptRepository: CrudRepository<Receipt, Long> {

}
