package ar.com.flow.bankaccount.adapters.`in`.web

import ar.com.flow.bankaccount.usecases.Deposit
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DepositController(private val deposit: Deposit) {
    @PostMapping("/savings-accounts/deposit/{customerName}/{currency}/{amount}")
    fun deposit(
        @PathVariable("customerName") customerName: String,
        @PathVariable("currency") currency: String,
        @PathVariable("amount") amount: Int
    ) {
        deposit.deposit(customerName, currency, amount)
    }
}
