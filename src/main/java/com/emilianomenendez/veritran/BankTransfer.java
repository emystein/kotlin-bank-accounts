package com.emilianomenendez.veritran;

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

    public void transfer(Dollars amountToTransfer) throws InsufficientFundsException {
        assertAccountsAreDifferent();

        debitAccount.withdraw(amountToTransfer);

        creditAccount.deposit(amountToTransfer);
    }

    private void assertAccountsAreDifferent() {
        if (debitAccount.equals(creditAccount)) {
            throw new SameAccountException();
        }
    }
}
