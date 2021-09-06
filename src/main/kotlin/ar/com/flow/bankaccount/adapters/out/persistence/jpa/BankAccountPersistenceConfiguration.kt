package ar.com.flow.bankaccount.adapters.out.persistence.jpa

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(basePackages = ["ar.com.flow.bankaccount.adapters.out.persistence.jpa"])
@EntityScan(basePackages = ["ar.com.flow.bankaccount.adapters.out.persistence.jpa"])
class BankAccountPersistenceConfiguration {
}