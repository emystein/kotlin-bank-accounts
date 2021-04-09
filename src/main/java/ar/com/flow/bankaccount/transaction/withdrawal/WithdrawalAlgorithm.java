package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.money.Money;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class WithdrawalAlgorithm implements Algorithm {
    public TransactionRecord execute(Money amount) {
        return new TransactionRecord(LocalDateTime.now(), Action.Withdrawal, Balance.negative(amount));
    }
}
