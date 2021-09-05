package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.adapters.out.AccountNotFound
import ar.com.flow.bankaccount.domain.TestObjects.francisco
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class InMemoryBankAccountsTest {
    @Test
    internal fun createAccount() {
        val bankAccounts = InMemoryBankAccounts()

        val createdAccount = bankAccounts.create(francisco, "ARS")

        assertTrue(bankAccounts.contains(createdAccount))
    }

    @Test
    internal fun throwExceptionOnCustomerNotOwningAnyAccount() {
        val bankAccounts = InMemoryBankAccounts()

        assertThrows(AccountNotFound::class.java) {
            bankAccounts.accountOwnedBy(francisco, "ARS")
        }
    }

    @Test
    internal fun throwExceptionOnAccountNotFoundForACurrency() {
        val bankAccounts = InMemoryBankAccounts()

        bankAccounts.create(francisco, "ARS")

        assertThrows(AccountNotFound::class.java) {
            bankAccounts.accountOwnedBy(francisco, "USD")
        }
    }
}