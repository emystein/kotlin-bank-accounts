package ar.com.flow.bankaccount.transaction.withdrawal;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.balance.Balance;
import ar.com.flow.bankaccount.transaction.Action;
import ar.com.flow.bankaccount.transaction.Algorithm;
import ar.com.flow.bankaccount.transaction.TransactionRecord;
import ar.com.flow.money.Money;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class WithdrawalAlgorithm implements Algorithm {
    private final BankAccount account;

    public TransactionRecord execute(Money amount) {
        return new TransactionRecord(LocalDateTime.now(), Action.Withdrawal, Balance.negative(amount));
    }
}