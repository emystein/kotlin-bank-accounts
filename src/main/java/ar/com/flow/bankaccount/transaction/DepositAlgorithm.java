package ar.com.flow.bankaccount.transaction;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.money.Money;

import java.time.LocalDateTime;

public class DepositAlgorithm implements Algorithm {
    public TransactionRecord execute(Money amount) {
        return new TransactionRecord(LocalDateTime.now(), Action.Deposit, Balance.positive(amount));
    }
}
