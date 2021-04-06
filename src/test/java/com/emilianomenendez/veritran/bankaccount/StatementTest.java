package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StatementTest {
    private Customer francisco;
    private Customer pedro;
    private SavingsAccount franciscosAccount;
    private SavingsAccount pedrosAccount;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        franciscosAccount = TestObjects.createSavingsAccountFor(francisco, dollars0);

        pedro = Customer.named("pedro");
        pedrosAccount = TestObjects.createSavingsAccountFor(pedro, dollars0);
    }

    @Test
    void givenAnAccountWhenMakeTwoDepositsThenTheStatementShouldContainTheTransactions() {
        franciscosAccount.deposit(dollars100);
        franciscosAccount.withdraw(dollars20);
        franciscosAccount.transfer(pedrosAccount, dollars50);
        franciscosAccount.deposit(dollars10);

        Collection<Transaction> statement = franciscosAccount.getStatement();

        assertTrue(statement.contains(new DepositTransaction(dollars100, dollars100)));
        assertTrue(statement.contains(new WithdrawalTransaction(dollars20, dollars80)));
        assertTrue(statement.contains(new TransferTransaction(dollars50, dollars30, pedro)));
        assertTrue(statement.contains(new DepositTransaction(dollars10, dollars40)));
    }
}
