package ar.com.flow.bankaccount.adapters.`in`.web

import ar.com.flow.bankaccount.usecases.Deposit
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
class DepositController(@Autowired private val deposit: Deposit) {
    @PostMapping("/savings-accounts/deposit/{accountOwner}/{currency}/{amount}")
    fun execute(
        @PathVariable("accountOwner") accountOwner: String,
        @PathVariable("currency") currency: String,
        @PathVariable("amount") amount: Int
    ) {
        deposit.execute(accountOwner, currency, amount)
    }
}
