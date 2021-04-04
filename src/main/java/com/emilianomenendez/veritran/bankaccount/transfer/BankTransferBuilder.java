package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.Money;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BankTransferBuilder {
    private final BankAccount debitAccount;
    private BankAccount creditAccount;

    public BankTransferBuilder to(BankAccount creditAccount) {
        this.creditAccount = creditAccount;

        return this;
    }

    public BankTransfer amount(Money amountToTransfer) {
        return new BankTransfer(debitAccount, creditAccount, amountToTransfer);
    }
}
