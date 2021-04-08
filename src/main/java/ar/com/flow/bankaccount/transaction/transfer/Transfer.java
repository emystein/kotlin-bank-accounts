package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.TransactionReason;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Transfer {
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

        public Transaction amount(Money amountToTransfer) {
            return new Transaction(creditAccount,
                    new DifferentAccounts(debitAccount, creditAccount),
                    new TransferFunds(debitAccount, creditAccount, amountToTransfer),
                    TransactionReason.Transfer,
                    Balance.positive(amountToTransfer));
        }
    }
}
