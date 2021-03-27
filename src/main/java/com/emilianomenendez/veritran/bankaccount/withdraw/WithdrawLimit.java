package com.emilianomenendez.veritran.bankaccount.withdraw;

import com.emilianomenendez.veritran.bankaccount.Balance;
import com.emilianomenendez.veritran.money.Dollars;

public interface WithdrawLimit {
    boolean reached(Balance balance, Dollars amountToWithdraw);
}
