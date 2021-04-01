package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Dollars;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final WithdrawalLimit withdrawalLimit;
    private Balance initialBalance;
    private Balance balance;
    private List<AccountMovement> history = new ArrayList<>();

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, WithdrawalLimit withdrawalLimit, Dollars initialBalance) {
        this.owner = owner;
        this.withdrawalLimit = withdrawalLimit;
        this.initialBalance = Balance.create(initialBalance);
        this.balance = Balance.create(initialBalance);
    }

    public boolean isOwnedBy(Customer customer) {
        return owner.equals(customer);
    }

    @Override
    public Balance getInitialBalance() {
        return initialBalance;
    }

    @Override
    public Balance getBalance() {
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

    public boolean historyContains(AccountMovement movement) {
        return history.contains(movement);
    }

    public boolean historyContainsInOrder(AccountMovement...  movements) {
        List<Integer> indices = Stream.of(movements).map(movement -> history.indexOf(movement)).sorted().collect(Collectors.toList());
        List<Integer> sortedIndices = Stream.of(movements).map(movement -> history.indexOf(movement)).collect(Collectors.toList());
        return indices.equals(sortedIndices);
    }
}
