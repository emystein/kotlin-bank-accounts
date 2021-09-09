package ar.com.flow.bankaccount.adapters.out.persistence.memory

import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.bankaccount.ports.out.BankAccounts
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InMemoryPersistenceConfiguration {
    @Bean
    fun customers(): Customers {
        return InMemoryCustomers()
    }

    @Bean
    fun savingsAccounts(): BankAccounts {
        return InMemoryBankAccounts()
    }
}