package ar.com.flow.bankaccount.domain

import assertk.assertThat
import org.junit.jupiter.api.Test

class BankRegistryTest {
    @Test
    fun addABankToTheRegistry() {
        val bankRegistry = InMemoryBankRegistry()

        val bbva = Bank
            .withName("BBVA")
            .withCode("1")
            .create()

        bankRegistry.add(bbva)

        assertThat(bankRegistry).contains(bbva)
    }
}