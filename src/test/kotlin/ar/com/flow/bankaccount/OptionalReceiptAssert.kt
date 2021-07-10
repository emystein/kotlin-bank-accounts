package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.transaction.receipt.Receipt
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions
import java.util.*

class OptionalReceiptAssert(statement: Optional<Receipt>) :
    AbstractAssert<OptionalReceiptAssert, Optional<Receipt>>(statement, OptionalReceiptAssert::class.java) {

    fun isPresent(): ReceiptAssert {
        Assertions.assertThat(actual).isPresent
        return ReceiptAssert(actual.get())
    }

    companion object {
        fun assertThat(statement: Optional<Receipt>): OptionalReceiptAssert {
            return OptionalReceiptAssert(statement)
        }
    }
}