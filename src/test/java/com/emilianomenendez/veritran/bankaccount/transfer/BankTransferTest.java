package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.Dollars;
import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.bankaccount.InsufficientFundsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.TestObjects.createBankAccountFor;
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
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        BankTransfer.from(debitAccount)
                .to(creditAccount)
                .transfer(Dollars.amount(10));

        assertTrue(debitAccount.hasBalance(Dollars.amount(90)));
        assertTrue(creditAccount.hasBalance(Dollars.amount(110)));
    }

    @Test
    void givenDebitWithInsufficientFundsWhenTransferThenMoneyShouldNotBeMoved() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));
        BankAccount creditAccount = createBankAccountFor(mabel, Dollars.amount(100));

        assertThrows(InsufficientFundsException.class, () ->
                BankTransfer.from(debitAccount)
                        .to(creditAccount)
                        .transfer(Dollars.amount(200)));

        assertTrue(debitAccount.hasBalance(Dollars.amount(100)));
        assertTrue(creditAccount.hasBalance(Dollars.amount(100)));
    }

    @Test
    void givenSameDebitAndCreditAccountWhenTransferThenMoneyShouldNotBeMoved() {
        BankAccount debitAccount = createBankAccountFor(francisco, Dollars.amount(100));

        assertThrows(SameAccountTransferException.class, () ->
                BankTransfer.from(debitAccount)
                        .to(debitAccount)
                        .transfer(Dollars.amount(10)));

        assertTrue(debitAccount.hasBalance(Dollars.amount(100)));
    }
}
