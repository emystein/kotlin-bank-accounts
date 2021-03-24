package com.emilianomenendez.veritran.bankaccount.transfer;

import com.emilianomenendez.veritran.Dollars;
import com.emilianomenendez.veritran.bankaccount.SavingsAccount;

public class BankTransfer {
    private SavingsAccount debitAccount;
    private SavingsAccount creditAccount;

    public static BankTransferBuilder from(SavingsAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    public BankTransfer(SavingsAccount debitAccount, SavingsAccount creditAccount) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
    }

    public void transfer(Dollars amountToTransfer) {
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
