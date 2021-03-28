package com.emilianomenendez.veritran.bankaccount.withdrawal;

public interface WithdrawalLimit {
    boolean accepts(Withdrawal withdrawal);
}
