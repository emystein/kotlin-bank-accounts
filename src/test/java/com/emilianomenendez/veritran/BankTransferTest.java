package com.emilianomenendez.veritran;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.TestObjects.createBankAccountFor;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void givenDebitAndCreditAccountWhenTransferThenMoneyShouldBeMovedFromDebitAccountToCreditAccount() throws InsufficientFundsException {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        BankTransfer.from(debitAccount)
                .to(creditAccount)
                .transfer(Dollars.amount(10));

        assertEquals(Dollars.amount(90), debitAccount.getBalance());
        assertEquals(Dollars.amount(110), creditAccount.getBalance());
    }

    @Test
    void givenSameDebitAndCreditAccountWhenTransferThenMoneyShouldNotBeMoved() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertThrows(SameAccountTransferException.class, () ->
                BankTransfer.from(debitAccount)
                        .to(debitAccount)
                        .transfer(Dollars.amount(10)));

        assertEquals(Dollars.amount(100), debitAccount.getBalance());
    }
}
