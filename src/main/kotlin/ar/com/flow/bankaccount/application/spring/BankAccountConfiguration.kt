package ar.com.flow.bankaccount.application.spring

import ar.com.flow.bankaccount.ports.out.Customers
import ar.com.flow.bankaccount.ports.out.BankAccounts
import ar.com.flow.bankaccount.usecases.Deposit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = ["ar.com.flow.bankaccount.adapters.in.web"])
class BankAccountConfiguration {
    @Autowired
    private lateinit var customers: Customers

    @Autowired
    private lateinit var bankAccounts: BankAccounts

    @Bean
    fun deposit(): Deposit {
        return Deposit(customers, bankAccounts)
    }
}