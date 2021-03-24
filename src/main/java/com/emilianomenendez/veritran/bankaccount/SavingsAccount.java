package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.money.Dollars;

public class SavingsAccount {
    private final Customer owner;
    private Dollars initialBalance;
    private Dollars balance;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, Dollars initialBalance) {
        this.owner = owner;
        this.initialBalance = initialBalance;
        this.balance = initialBalance;
    }

    public boolean isOwnedBy(Customer customer) {
        return owner.equals(customer);
    }

    public Dollars getInitialBalance() {
        return initialBalance;
    }

    public Dollars getBalance() {
        return balance;
    }

    public void deposit(Dollars amountToDeposit) {
        balance = balance.plus(amountToDeposit);
    }

    public void withdraw(Dollars amountToWithdraw) {
        balance = balance.minus(amountToWithdraw);
    }

    public void transfer(SavingsAccount creditAccount, Dollars amountToTransfer) {
        BankTransfer.from(this)
                .to(creditAccount)
                .transfer(amountToTransfer);
    }
}
