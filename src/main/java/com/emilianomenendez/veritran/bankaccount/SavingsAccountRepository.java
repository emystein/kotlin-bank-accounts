package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;

public interface SavingsAccountRepository {
    void create(SavingsAccount account);

    SavingsAccount ownedBy(Customer accountOwner);

    void update(SavingsAccount savingsAccount);
}
