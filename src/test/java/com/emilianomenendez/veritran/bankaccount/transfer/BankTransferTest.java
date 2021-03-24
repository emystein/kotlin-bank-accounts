package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.TestObjects;
import com.emilianomenendez.veritran.bankaccount.SavingsAccount;
import com.emilianomenendez.veritran.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.*;
import static com.emilianomenendez.veritran.bankaccount.SavingsAccountAssertions.assertAccountKeepsInitialBalance;
import static com.emilianomenendez.veritran.bankaccount.TestObjects.*;
import static org.junit.jupiter.api.Assertions.*;

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
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, TestObjects.dollars100);
        SavingsAccount creditAccount = createSavingsAccountFor(mabel, TestObjects.dollars100);

        BankTransfer.from(debitAccount)
                .to(creditAccount)
                .transfer(TestObjects.dollars10);

        assertBalanceDecreasedBy(debitAccount, TestObjects.dollars10);
        assertBalanceIncreasedBy(creditAccount, TestObjects.dollars10);
    }

    @Test
    void givenDebitWithInsufficientFundsWhenTransferThenMoneyShouldNotBeMoved() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, TestObjects.dollars100);
        SavingsAccount creditAccount = createSavingsAccountFor(mabel, TestObjects.dollars100);

        assertThrows(InsufficientFundsException.class, () ->
                BankTransfer.from(debitAccount)
                        .to(creditAccount)
                        .transfer(TestObjects.dollars200));

        assertAccountKeepsInitialBalance(debitAccount);
        assertAccountKeepsInitialBalance(creditAccount);
    }

    @Test
    void givenSameDebitAndCreditAccountWhenTransferThenMoneyShouldNotBeMoved() {
        SavingsAccount debitAccount = createSavingsAccountFor(francisco, TestObjects.dollars100);

        assertThrows(SameAccountTransferException.class, () ->
                BankTransfer.from(debitAccount)
                        .to(debitAccount)
                        .transfer(TestObjects.dollars10));

        assertAccountKeepsInitialBalance(debitAccount);
    }
}
