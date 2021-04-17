package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Step;
import ar.com.flow.bankaccount.transaction.receipt.CreditPrinter;
import ar.com.flow.bankaccount.transaction.receipt.DebitPrinter;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.withdrawal.SufficientFunds;
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
            return Transaction.builder()
                    .precondition(new SufficientFunds(debitAccount, amountToTransfer))
                    .precondition(new DifferentAccounts(debitAccount, creditAccount))
                    .step(new Step(debitAccount, Transfer.debitReceipt(creditAccount)))
                    .step(new Step(creditAccount, Transfer.creditReceipt(creditAccount)))
                    .amount(amountToTransfer)
                    .build();
        }
    }

    public static DebitPrinter debitReceipt(BankAccount account) {
        return new DebitPrinter(account, Action.Transfer);
    }

    public static CreditPrinter creditReceipt(BankAccount account) {
        return new CreditPrinter(account, Action.Transfer);
    }
}
