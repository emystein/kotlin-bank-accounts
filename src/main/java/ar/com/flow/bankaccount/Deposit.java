package ar.com.flow.bankaccount;

import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static java.time.LocalDateTime.now;

@RequiredArgsConstructor
@Getter
public class Deposit implements Transaction {
    private final BankAccount creditAccount;
    private final Money amount;

    public static DepositBuilder to(BankAccount creditAccount) {
        return new DepositBuilder(creditAccount);
    }

    @RequiredArgsConstructor
    public static class DepositBuilder {
        private final BankAccount creditAccount;

        public Deposit amount(Money amountToDeposit) {
            return new Deposit(creditAccount, amountToDeposit);
        }
    }

    @Override
    public TransactionRecord execute() {
        var transactionRecord = new TransactionRecord(now(), amount);

        creditAccount.addTransactionRecord(transactionRecord);

        return transactionRecord;
    }
}
