package ar.com.flow.bankaccount;

import ar.com.flow.bankaccount.transaction.Step;
import ar.com.flow.bankaccount.transaction.Transaction;
import ar.com.flow.bankaccount.transaction.receipt.Action;
import ar.com.flow.bankaccount.transaction.receipt.CreditPrinter;
import ar.com.flow.bankaccount.transaction.receipt.DebitPrinter;
import ar.com.flow.bankaccount.transfer.DifferentAccounts;
import ar.com.flow.bankaccount.withdrawal.SufficientFunds;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

class Transfer {
    static BankTransferBuilder from(BankAccount debitAccount) {
        return new BankTransferBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    static class BankTransferBuilder {
        private final BankAccount debitAccount;
        private BankAccount creditAccount;

        BankTransferBuilder to(BankAccount creditAccount) {
            this.creditAccount = creditAccount;
            return this;
        }

        Transaction amount(Money amountToTransfer) {
            return Transaction.builder()
                    .precondition(new SufficientFunds(debitAccount, amountToTransfer))
                    .precondition(new DifferentAccounts(debitAccount, creditAccount))
                    .step(new Step(debitAccount, Transfer.debitReceipt(creditAccount)))
                    .step(new Step(creditAccount, Transfer.creditReceipt(creditAccount)))
                    .amount(amountToTransfer)
                    .build();
        }
    }

    static DebitPrinter debitReceipt(BankAccount account) {
        return new DebitPrinter(account, Action.Transfer);
    }

    static CreditPrinter creditReceipt(BankAccount account) {
        return new CreditPrinter(account, Action.Transfer);
    }
}
