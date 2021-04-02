package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.InsufficientFundsException;
import com.emilianomenendez.veritran.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Withdrawal {
    private final BankAccount debitAccount;
    private final Money amount;

    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
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
