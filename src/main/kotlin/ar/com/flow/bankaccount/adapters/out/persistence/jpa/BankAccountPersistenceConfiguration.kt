package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import ar.com.flow.bankaccount.adapters.out.UUIDAccountIdGenerator
import ar.com.flow.bankaccount.ports.out.AccountIdGenerator
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan
@EnableJpaRepositories
@EntityScan
class BankAccountPersistenceConfiguration {
    @Bean
    fun accountIdGenerator(): AccountIdGenerator {
        return UUIDAccountIdGenerator()
    }
}