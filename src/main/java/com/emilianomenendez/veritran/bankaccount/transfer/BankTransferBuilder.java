package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BankTransferBuilder {
    private final BankAccount debitAccount;

    public BankTransfer to(BankAccount creditAccount) {
        return new BankTransfer(debitAccount, creditAccount);
    }
}
