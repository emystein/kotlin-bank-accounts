package com.emilianomenendez.veritran.bankaccount.withdrawal;

import com.emilianomenendez.veritran.money.Dollars;
import com.emilianomenendez.veritran.money.Number;

public interface WithdrawalLimit {
    boolean supports(Dollars amountToWithdraw, Number availableFunds);
}
