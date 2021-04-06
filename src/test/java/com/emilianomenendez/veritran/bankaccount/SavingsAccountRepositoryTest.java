package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.emilianomenendez.veritran.bankaccount.money.TestObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsAccountRepositoryTest {
    private Customer francisco;
    private Customer mabel;
    private SavingsAccount account;
    private SavingsAccountRepository savingsAccountRepository;

    @BeforeEach
    void setUp() {
        francisco = Customer.named("francisco");
        mabel = Customer.named("mabel");

        account = TestObjects.createSavingsAccountFor(francisco, dollars100);

        savingsAccountRepository = new InMemorySavingsAccountRepository();
    }

    @Test
    void givenANewSavingsAccountWhenSaveItToTheRepositoryThenTheRepositoryShouldContainTheAccount() {
        savingsAccountRepository.create(account);

        SavingsAccount persistedAccount = savingsAccountRepository.ownedBy(francisco);

        assertEquals(account, persistedAccount);
    }

    @Test
    void givenADepositWhenSaveTheAccountToTheRepositoryThenTheRepositoryShouldContainTheUpdatedAccount() {
        savingsAccountRepository.create(account);

        SavingsAccount persistedAccount = savingsAccountRepository.ownedBy(francisco);
        persistedAccount.deposit(dollars10);

        savingsAccountRepository.update(persistedAccount);

        SavingsAccount updatedAccount = savingsAccountRepository.ownedBy(francisco);
        assertEquals(dollars110, updatedAccount.getBalance());
    }
}
