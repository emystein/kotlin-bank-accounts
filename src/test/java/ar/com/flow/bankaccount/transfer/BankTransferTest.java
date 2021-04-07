package ar.com.flow.bankaccount.transfer;

import ar.com.flow.Customer;
import ar.com.flow.bankaccount.SavingsAccountAssertions;
import ar.com.flow.bankaccount.TestObjects;
import ar.com.flow.money.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ar.com.flow.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankTransferTest {
    private Customer francisco;
    private Customer mabel;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        mabel = Customer.named("mabel");
    }

    @Test
    void givenDebitAndCreditAccountWhenTransferThenMoneyShouldBeMovedFromDebitAccountToCreditAccount() {
        var debitAccount = TestObjects.createSavingsAccountFor(francisco, dollars100);
        var creditAccount = TestObjects.createSavingsAccountFor(mabel, dollars100);

        BankTransfer.from(debitAccount)
                .to(creditAccount)
                .amount(dollars10)
                .execute();

        SavingsAccountAssertions.assertBalanceDecreasedBy(debitAccount, dollars10);
        SavingsAccountAssertions.assertBalanceIncreasedBy(creditAccount, dollars10);
    }

    @Test
    void givenDebitWithInsufficientFundsWhenTransferThenMoneyShouldNotBeMoved() {
        var debitAccount = TestObjects.createSavingsAccountFor(francisco, dollars100);
        var creditAccount = TestObjects.createSavingsAccountFor(mabel, dollars100);

        assertThrows(InsufficientFundsException.class, () ->
                BankTransfer.from(debitAccount)
                        .to(creditAccount)
                        .amount(dollars200)
                        .execute());

        SavingsAccountAssertions.assertAccountKeepsInitialBalance(debitAccount);
        SavingsAccountAssertions.assertAccountKeepsInitialBalance(creditAccount);
    }

    @Test
    void givenSameDebitAndCreditAccountWhenTransferThenMoneyShouldNotBeMoved() {
        var debitAccount = TestObjects.createSavingsAccountFor(francisco, dollars100);

        assertThrows(SameAccountTransferException.class, () ->
                BankTransfer.from(debitAccount)
                        .to(debitAccount)
                        .amount(dollars10)
                        .execute());

        SavingsAccountAssertions.assertAccountKeepsInitialBalance(debitAccount);
    }
}
