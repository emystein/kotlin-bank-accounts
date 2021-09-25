package ar.com.flow.bankaccount.application.spring

import ar.com.flow.bankaccount.adapters.out.persistence.jpa.BankAccountPersistenceConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(BankAccountPersistenceConfiguration::class)
class BankAccountApplication

fun main(args: Array<String>) {
    runApplication<BankAccountApplication>(*args)
}
