package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import com.emilianomenendez.veritran.money.Money;

public class Withdrawal {
    private final BankAccount debitAccount;
    private final Money amount;

    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    public Withdrawal(BankAccount debitAccount, Money amount) {
        this.debitAccount = debitAccount;
        this.amount = amount;
    }

    public BankAccount getDebitAccount() {
        return debitAccount;
    }

    public Money getAmount() {
        return amount;
    }

    public Balance previewBalanceAfter() {
        return getDebitAccount().getBalance().minus(amount);
    }

    public Balance execute() {
        if (!debitAccount.withdrawalLimitAccepts(this)) {
            throw new InsufficientFundsException();
        }

        return debitAccount.getBalance().minus(amount);
    }
}
