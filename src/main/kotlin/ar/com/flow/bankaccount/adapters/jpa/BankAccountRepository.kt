package ar.com.flow.bankaccount.adapters.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BankAccountRepository : CrudRepository<JpaBankAccount, Long> {

}
