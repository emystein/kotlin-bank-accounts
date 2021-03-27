package com.emilianomenendez.veritran.bankaccount;

import com.emilianomenendez.veritran.money.Dollars;

public interface WithdrawLimit {
    boolean reached(Balance balance, Dollars amountToWithdraw);
}
