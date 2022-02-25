package ar.com.flow.bankaccount.adapters.out

import ar.com.flow.bankaccount.domain.AccountId
import ar.com.flow.bankaccount.ports.out.AccountIdGenerator
import java.util.*

class UUIDAccountIdGenerator: AccountIdGenerator {
    override fun generate(): AccountId {
        return AccountId(UUID.randomUUID().toString())
    }
}