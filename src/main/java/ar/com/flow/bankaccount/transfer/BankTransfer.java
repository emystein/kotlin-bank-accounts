package ar.com.flow.bankaccount.transfer;

import ar.com.flow.bankaccount.Balance;
import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.Transaction;
import ar.com.flow.bankaccount.TransactionReason;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class BankTransfer extends Transaction {
    public BankTransfer(BankAccount debitAccount, BankAccount creditAccount, Money amount) {
        super(creditAccount,
                new DifferentAccounts(debitAccount, creditAccount),
                new TransferFunds(debitAccount, creditAccount, amount),
                TransactionReason.TransferCredit,
                Balance.positive(amount));
    }

    public static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class BankTransferBuilder {
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
}
