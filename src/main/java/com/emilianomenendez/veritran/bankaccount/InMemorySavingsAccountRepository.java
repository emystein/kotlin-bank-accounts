package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;

import java.util.HashMap;
import java.util.Map;

public class InMemorySavingsAccountRepository implements SavingsAccountRepository {
    private Map<Customer, SavingsAccount> savingsAccount = new HashMap<>();

    @Override
    public void create(SavingsAccount account) {
        savingsAccount.put(account.getOwner(), account);
    }

    @Override
    public SavingsAccount ownedBy(Customer accountOwner) {
        return savingsAccount.get(accountOwner);
    }

    @Override
    public void update(SavingsAccount account) {
        savingsAccount.put(account.getOwner(), account);
    }
}
