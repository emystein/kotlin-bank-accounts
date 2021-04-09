package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
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

        public TransferTransaction amount(Money amountToTransfer) {
            return TransferTransaction.builder()
                    .debitAccount(debitAccount)
                    .creditAccount(creditAccount)
                    .amount(amountToTransfer)
                    .preconditions(new DifferentAccounts(debitAccount, creditAccount))
                    .build();
        }
    }
}
