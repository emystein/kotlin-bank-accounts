package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.money.Dollars;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class SavingsAccount {
    private final Customer owner;
    private Dollars initialBalance;
    private Dollars balance;
    private Collection<Transaction> statement;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, Dollars initialBalance) {
        this.owner = owner;
        this.initialBalance = initialBalance;
        this.balance = initialBalance;
        this.statement = new ArrayList<>();
    }

    public Customer getOwner() {
        return owner;
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

        statement.add(new DepositTransaction(amountToDeposit, balance));
    }

    public void withdraw(Dollars amountToWithdraw) {
        balance = balance.minus(amountToWithdraw);

        statement.add(new WithdrawalTransaction(amountToWithdraw, balance));
    }

    public void transfer(SavingsAccount creditAccount, Dollars amountToTransfer) {
        BankTransfer.from(this)
                .to(creditAccount)
                .transfer(amountToTransfer);

        statement.add(new TransferTransaction(amountToTransfer, balance, creditAccount.getOwner()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingsAccount)) return false;
        SavingsAccount that = (SavingsAccount) o;
        return owner.equals(that.owner) && balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, balance);
    }

    public Collection<Transaction> getStatement() {
        return statement;
    }
}
