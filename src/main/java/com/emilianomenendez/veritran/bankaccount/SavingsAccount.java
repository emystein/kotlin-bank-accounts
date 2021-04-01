package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Money;

import java.time.LocalDateTime;

public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final WithdrawalLimit withdrawalLimit;
    private Money initialBalance;
    private Money balance;
    private AccountHistory history;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, WithdrawalLimit withdrawalLimit, Money initialBalance, AccountHistory history) {
        this.owner = owner;
        this.withdrawalLimit = withdrawalLimit;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = Balance.create(initialBalance);
        this.history = history;
    }

    public boolean isOwnedBy(Customer customer) {
        return owner.equals(customer);
    }

    @Override
    public Money getInitialBalance() {
        return initialBalance;
    }

    @Override
    public Money getBalance() {
        return balance;
    }

    @Override
    public AccountMovement deposit(Dollars amountToDeposit) {
        balance = balance.plus(amountToDeposit);

        AccountMovement movement = new AccountMovement(LocalDateTime.now(), amountToDeposit);
        history.add(movement);
        return movement;
    }

    @Override
    public boolean withdrawalLimitAccepts(Withdrawal withdrawal) {
        return withdrawalLimit.accepts(withdrawal);
    }

    @Override
    public AccountMovement withdraw(Dollars amountToWithdraw) {
        balance = Withdrawal.from(this)
                .amount(amountToWithdraw)
                .execute();

        AccountMovement movement = new AccountMovement(LocalDateTime.now(), Balance.negative(amountToWithdraw));
        history.add(movement);
        return movement;
    }

    @Override
    public void transfer(BankAccount creditAccount, Dollars amountToTransfer) {
        BankTransfer.from(this)
                .to(creditAccount)
                .transfer(amountToTransfer);
    }
}
