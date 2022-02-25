package ar.com.flow.bankaccount.ports.out

import ar.com.flow.bankaccount.domain.AccountId

interface AccountIdGenerator {
    fun generate(): AccountId
}