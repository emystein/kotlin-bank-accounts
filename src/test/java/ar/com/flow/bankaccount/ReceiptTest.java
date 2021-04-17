package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.receipt.Action;
import ar.com.flow.bankaccount.transaction.receipt.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor;
import static ar.com.flow.bankaccount.TestObjects.francisco;
import static ar.com.flow.bankaccount.TransactionRecordAssert.assertThat;
import static ar.com.flow.money.TestObjects.*;

public class ReceiptTest {
    private BankAccount franciscosAccount;

    @BeforeEach
    void setUp() {
        franciscosAccount = createSavingsAccountFor(francisco, dollars100);
    }

    @Test
    void recordCreditResultBalance() {
        var receipt = Receipt.credit(franciscosAccount, Action.Deposit, dollars10);

        assertThat(receipt).hasResultBalance(Balance.positive(dollars110));
    }

    @Test
    void recordDebitResultBalance() {
        var receipt = Receipt.debit(franciscosAccount, Action.Deposit, dollars10);

        assertThat(receipt).hasResultBalance(Balance.positive(dollars90));
    }
}
