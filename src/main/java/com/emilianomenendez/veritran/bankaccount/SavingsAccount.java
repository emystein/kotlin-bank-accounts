package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.Customer;
import com.emilianomenendez.veritran.bankaccount.transfer.BankTransfer;
import com.emilianomenendez.veritran.bankaccount.withdrawal.Withdrawal;
import com.emilianomenendez.veritran.bankaccount.withdrawal.WithdrawalLimit;
import com.emilianomenendez.veritran.money.Money;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SavingsAccount implements BankAccount {
    private final Customer owner;
    private final WithdrawalLimit withdrawalLimit;
    private final TransactionHistory transactionHistory;

    public static SavingsAccountBuilder ownedBy(Customer owner) {
        return new SavingsAccountBuilder(owner);
    }

    public SavingsAccount(Customer owner, WithdrawalLimit withdrawalLimit, Money initialBalance, TransactionHistory transactionHistory) {
        this.owner = owner;
        this.withdrawalLimit = withdrawalLimit;
        this.transactionHistory = transactionHistory;
        this.transactionHistory.add(new TransactionRecord(LocalDateTime.now(), initialBalance));
    }

    public boolean isOwnedBy(Customer customer) {
        return owner.equals(customer);
    }

    public Balance getInitialBalance() {
        return transactionHistory.first().getAmount();
    }

    public Balance getBalance() {
        return transactionHistory.sum();
    }

    public Balance getPreviousBalance() {
        return transactionHistory.sumBeforeLastTransaction();
    }

    public TransactionRecord deposit(Money amountToDeposit) {
        return Deposit.to(this).amount(amountToDeposit).execute();
    }

    public boolean withdrawalLimitAccepts(Withdrawal withdrawal) {
        return withdrawalLimit.accepts(withdrawal);
    }

    public TransactionRecord withdraw(Money amountToWithdraw) {
        return Withdrawal.from(this).amount(amountToWithdraw).execute();
    }

    public TransactionRecord transfer(BankAccount creditAccount, Money amountToTransfer) {
        return BankTransfer.from(this)
                .to(creditAccount)
                .amount(amountToTransfer)
                .execute();
    }
}
