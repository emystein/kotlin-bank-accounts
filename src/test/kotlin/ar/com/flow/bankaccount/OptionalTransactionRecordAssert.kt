package ar.com.flow.bankaccount

import ar.com.flow.bankaccount.transaction.receipt.Receipt
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.Assertions
import java.util.*

class OptionalTransactionRecordAssert(statement: Optional<Receipt>) :
    AbstractAssert<OptionalTransactionRecordAssert?, Optional<Receipt>>(
        statement,
        OptionalTransactionRecordAssert::class.java
    ) {

    fun isPresent(): ReceiptAssert {
        Assertions.assertThat(actual).isPresent
        return ReceiptAssert(actual.get())
    }

    companion object {
        fun assertThat(statement: Optional<Receipt>): OptionalTransactionRecordAssert {
            return OptionalTransactionRecordAssert(statement)
        }
    }
}