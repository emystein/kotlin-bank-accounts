package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.receipt.DebitStamper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.bankaccount.BankAccountAssert.assertThat;
import static ar.com.flow.bankaccount.TestObjects.createSavingsAccountFor;
import static ar.com.flow.bankaccount.TestObjects.francisco;
import static ar.com.flow.money.TestObjects.dollars10;
import static ar.com.flow.money.TestObjects.dollars100;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionTest {
    private BankAccount debitAccount;
    private Algorithm debit;
    private Algorithm creditMock;

    @BeforeEach
    void setUp() {
        debitAccount = createSavingsAccountFor(francisco, dollars100);
        debit = new Algorithm(debitAccount, new DebitStamper(debitAccount, Action.Transfer));
        creditMock = mock(Algorithm.class);
    }

    @Test
    void rollbackAfterFirstStep() {
        when(creditMock.execute(dollars10)).thenThrow(new RuntimeException());

        Transaction.builder()
                .step(debit)
                .step(creditMock)
                .amount(dollars10)
                .build()
                .execute();

        assertThat(debitAccount).keepsInitialBalance();
    }
}
