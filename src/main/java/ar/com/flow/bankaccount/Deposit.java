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

    public static DepositBuilder to(BankAccount bankAccount) {
        return new DepositBuilder(bankAccount);
    }

    public static class DepositBuilder {
        private final BankAccount targetAccount;

        public DepositBuilder(BankAccount targetAccount) {
            this.targetAccount = targetAccount;
        }

        public Deposit amount(Money amountToDeposit) {
            return new Deposit(targetAccount, amountToDeposit);
        }
    }

    @Override
    public TransactionRecord execute() {
        var transactionRecord = new TransactionRecord(now(), amount);

        creditAccount.addTransactionRecord(transactionRecord);

        return transactionRecord;
    }
}
