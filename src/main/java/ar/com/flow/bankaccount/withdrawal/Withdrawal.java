package ar.com.flow.bankaccount.withdrawal;

import ar.com.flow.bankaccount.Balance;
import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.Transaction;
import ar.com.flow.bankaccount.TransactionRecord;
import ar.com.flow.money.InsufficientFundsException;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@Getter
public class Withdrawal implements Transaction {
    private final BankAccount debitAccount;
    private final Money amount;
    private final WithdrawalLimit withdrawalLimit;

    public static WithdrawalBuilder from(BankAccount debitAccount) {
        return new WithdrawalBuilder(debitAccount);
    }

    @RequiredArgsConstructor
    public static class WithdrawalBuilder {
        private final BankAccount debitAccount;
        private WithdrawalLimit withdrawalLimit = new CurrentFundsLimit();

        public WithdrawalBuilder limit(WithdrawalLimit aWithdrawalLimit) {
            withdrawalLimit = aWithdrawalLimit;

            return this;
        }

        public Withdrawal amount(Money amountToWithdraw) {
            return new Withdrawal(debitAccount, amountToWithdraw, withdrawalLimit);
        }
    }

    public Balance previewBalanceAfter() {
        return debitAccount.getBalance().minus(amount);
    }

    @Override
    public TransactionRecord execute() {
        if (!withdrawalLimit.accepts(this)) {
            throw new InsufficientFundsException();
        }

        var transactionRecord = new TransactionRecord(now(), Balance.negative(amount));

        debitAccount.addTransactionRecord(transactionRecord);

        return transactionRecord;
    }
}
