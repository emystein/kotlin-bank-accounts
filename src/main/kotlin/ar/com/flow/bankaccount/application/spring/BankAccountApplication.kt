package ar.com.flow.bankaccount.application.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = ["ar.com.flow.bankaccount"])
class BankAccountApplication

fun main(args: Array<String>) {
    runApplication<BankAccountApplication>(*args)
}
