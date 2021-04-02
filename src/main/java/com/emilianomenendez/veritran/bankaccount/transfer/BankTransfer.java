package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.bankaccount.BankAccount;
import com.emilianomenendez.veritran.money.PositiveAmount;

public class BankTransfer {
    private BankAccount debitAccount;
    private BankAccount creditAccount;

    public static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    public BankTransfer(BankAccount debitAccount, BankAccount creditAccount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
    }

    public void transfer(PositiveAmount amountToTransfer) {
        assertAccountsAreDifferent();

        debitAccount.withdraw(amountToTransfer);

        creditAccount.deposit(amountToTransfer);
    }

    private void assertAccountsAreDifferent() {
        if (debitAccount.equals(creditAccount)) {
            throw new SameAccountTransferException();
        }
    }
}
