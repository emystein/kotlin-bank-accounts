package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.money.Money;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionRecordAssert extends AbstractAssert<TransactionRecordAssert, TransactionRecord> {
    public static TransactionRecordAssert assertThat(TransactionRecord statement) {
        return new TransactionRecordAssert(statement);
    }

    public TransactionRecordAssert(TransactionRecord record) {
        super(record, TransactionRecordAssert.class);
    }

    public TransactionRecordAssert hasAction(Action expected) {
        Assertions.assertThat(actual.getAction()).isEqualTo(expected);
        return this;
    }

    public TransactionRecordAssert hasBalance(Balance expected) {
        Assertions.assertThat(actual.getAmount()).isEqualTo(expected);
        return this;
    }

    public TransactionRecordAssert hasCreditAccount(BankAccount expected) {
        assertEquals(expected, actual.getCreditAccount().get());
        return this;
    }

    public TransactionRecordAssert isDeposit() {
        hasAction(Action.Deposit);
        return this;
    }

    public TransactionRecordAssert isWithdrawal() {
        hasAction(Action.Withdrawal);
        return this;
    }

    public TransactionRecordAssert isTransferDebit() {
        hasAction(Action.TransferDebit);
        return this;
    }

    public TransactionRecordAssert isTransferCredit() {
        hasAction(Action.TransferCredit);
        return this;
    }

    public TransactionRecordAssert hasPositiveBalance(Money amount) {
        hasBalance(Balance.positive(amount));
        return this;
    }

    public TransactionRecordAssert hasNegativeBalance(Money amount) {
        hasBalance(Balance.negative(amount));
        return this;
    }
}
