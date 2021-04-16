package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor;
import static ar.com.flow.bankaccount.TestObjects.francisco;
import static ar.com.flow.bankaccount.TransactionRecordAssert.assertThat;
import static ar.com.flow.money.TestObjects.*;

public class TransactionRecordTest {
    private BankAccount franciscosAccount;

    @BeforeEach
    void setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, dollars100);
    }

    @Test
    void recordCreditResultBalance() {
        var record = TransactionRecord.credit(franciscosAccount, Action.Deposit, dollars10);

        assertThat(record).hasResultBalance(Balance.positive(dollars110));
    }

    @Test
    void recordDebitResultBalance() {
        var record = TransactionRecord.debit(franciscosAccount, Action.Deposit, dollars10);

        assertThat(record).hasResultBalance(Balance.positive(dollars90));
    }
}
