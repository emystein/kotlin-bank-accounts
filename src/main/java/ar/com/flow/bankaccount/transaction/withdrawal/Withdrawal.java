package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.OnTransactionLog;
import ar.com.flow.bankaccount.transaction.TransactionLog;
import ar.com.flow.bankaccount.transaction.SingleTransaction;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

public class Withdrawal {
    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class WithdrawalBuilder {
        private final BankAccount debitAccount;
        private Money amountToWithdraw;
        private TransactionLog transactionLog = new OnTransactionLog();

        public WithdrawalBuilder amount(Money amountToWithdraw) {
            this.amountToWithdraw = amountToWithdraw;
            return this;
        }

        public WithdrawalBuilder recorder(TransactionLog transactionLog) {
            this.transactionLog = transactionLog;
            return this;
        }

        public SingleTransaction build() {
            return SingleTransaction.builder()
                    .account(debitAccount)
                    .amount(amountToWithdraw)
                    .algorithm(new WithdrawalAlgorithm(debitAccount))
                    .preconditions(new SufficientFunds(debitAccount, amountToWithdraw))
                    .transactionLog(transactionLog)
                    .build();
        }
    }
}
