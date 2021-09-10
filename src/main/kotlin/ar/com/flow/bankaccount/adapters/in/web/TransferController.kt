package ar.com.flow.bankaccount.adapters.`in`.web

import ar.com.flow.bankaccount.usecases.Transfer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Component
@RestController
class TransferController(@Autowired private val transfer: Transfer) {
    @PostMapping("/savings-accounts/transfer/{debitCustomer}/{currency}/{amount}/{creditCustomer}")
    fun execute(
        @PathVariable("debitCustomer") debitCustomer: String,
        @PathVariable("currency") currency: String,
        @PathVariable("amount") amount: Int,
        @PathVariable("creditCustomer") creditCustomer: String,
    ) {
        transfer.execute(debitCustomer, currency, amount, creditCustomer)
    }
}
