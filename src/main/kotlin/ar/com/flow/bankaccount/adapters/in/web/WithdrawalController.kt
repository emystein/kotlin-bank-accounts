package ar.com.flow.bankaccount.adapters.`in`.web

import ar.com.flow.bankaccount.usecases.Withdrawal
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
class WithdrawalController(@Autowired private val withdrawal: Withdrawal) {
    @PostMapping("/savings-accounts/withdrawal/{accountOwner}/{currency}/{amount}")
    fun execute(
        @PathVariable("accountOwner") accountOwner: String,
        @PathVariable("currency") currency: String,
        @PathVariable("amount") amount: Int
    ) {
        withdrawal.execute(accountOwner, currency, amount)
    }
}
