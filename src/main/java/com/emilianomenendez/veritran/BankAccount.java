package com.emilianomenendez.veritran;

public class BankAccount {
    private Customer owner;
    private Dollars balance;

    public static BankAccountBuilder newAccountOwnedBy(Customer accountOwner) {
        return new BankAccountBuilder(accountOwner);
    }

    public BankAccount(Customer owner, Dollars initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
    }

    public boolean isOwnedBy(Customer customer) {
        return this.owner == customer;
    }

    public Dollars getBalance() {
        return balance;
    }

    public void deposit(Dollars amountToDeposit) {
        balance = balance.plus(amountToDeposit);
    }

    public void withdraw(Dollars amountToWithdraw) throws InsufficientFundsException {
        assertSufficientFunds(amountToWithdraw);

        balance = balance.minus(amountToWithdraw);
    }

    public void transfer(BankAccount destinationAccount, Dollars amountToTransfer) throws InsufficientFundsException {
        BankTransfer.from(this)
                .to(destinationAccount)
                .transfer(amountToTransfer);
    }

    private void assertSufficientFunds(Dollars amountToWithdraw) throws InsufficientFundsException {
        if (amountToWithdraw.isGreaterThan(balance)) {
            throw new InsufficientFundsException();
        }
    }
}
