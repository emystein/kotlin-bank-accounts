package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.transaction.Receipt;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import java.util.Optional;

public class OptionalTransactionRecordAssert extends AbstractAssert<OptionalTransactionRecordAssert, Optional<Receipt>> {
    public static OptionalTransactionRecordAssert assertThat(Optional<Receipt> statement) {
        return new OptionalTransactionRecordAssert(statement);
    }
    
    public OptionalTransactionRecordAssert(Optional<Receipt> statement) {
        super(statement, OptionalTransactionRecordAssert.class);
    }

    public TransactionRecordAssert isPresent() {
        Assertions.assertThat(actual).isPresent();

        return new TransactionRecordAssert(actual.get());
    }
}
