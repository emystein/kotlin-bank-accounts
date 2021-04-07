package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithdrawalBuilder {
    private final BankAccount debitAccount;
    private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

    public WithdrawalBuilder limit(WithdrawalLimit aWithdrawalLimit) {
        withdrawalLimit = aWithdrawalLimit;

        return this;
    }

    public Withdrawal amount(Money amountToWithdraw) {
        return new Withdrawal(debitAccount, amountToWithdraw, withdrawalLimit);
    }
}
