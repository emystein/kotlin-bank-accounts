package com.emilianomenendez.veritran;

public class BankTransferBuilder {
    private BankAccount debitAccount;

    public BankTransferBuilder(BankAccount debitAccount) {
        this.debitAccount = debitAccount;
    }

    public BankTransfer to(BankAccount creditAccount) {
        return new BankTransfer(debitAccount, creditAccount);
    }
}
