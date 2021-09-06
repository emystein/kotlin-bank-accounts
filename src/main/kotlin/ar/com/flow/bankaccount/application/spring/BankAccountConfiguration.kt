package ar.com.flow.bankaccount.application.spring

import ar.com.flow.bankaccount.adapters.`in`.web.DepositController
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemoryCustomers
import ar.com.flow.bankaccount.adapters.out.persistence.memory.InMemorySavingsAccounts
import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.bankaccount.ports.out.SavingsAccounts
import ar.com.flow.bankaccount.usecases.Deposit
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableWebMvc
class BankAccountConfiguration {

    @Bean
    fun customers(): Customers {
        return InMemoryCustomers()
    }

    @Bean
    fun savingsAccounts(): SavingsAccounts {
        return InMemorySavingsAccounts()
    }

    @Bean
    fun deposit(): Deposit {
        return Deposit(customers(), savingsAccounts())
    }

    @Bean
    fun depositController(): DepositController {
        return DepositController(deposit())
    }
}