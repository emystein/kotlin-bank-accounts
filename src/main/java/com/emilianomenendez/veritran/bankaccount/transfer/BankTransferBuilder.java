package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.bankaccount.SavingsAccount;

public class BankTransferBuilder {
    private SavingsAccount debitAccount;

    public BankTransferBuilder(SavingsAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public BankTransfer to(SavingsAccount creditAccount) {
        return new BankTransfer(debitAccount, creditAccount);
    }
}
