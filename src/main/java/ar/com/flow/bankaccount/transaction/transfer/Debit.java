package ar.com.flow.bankaccount.transaction.transfer;

import ar.com.flow.bankaccount.BankAccount;
import ar.com.flow.bankaccount.transaction.FundsMovement;
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
public class Debit implements Algorithm {
    private final BankAccount account;
    private final BankAccount creditAccount;

    public TransactionRecord execute(Money amount) {
        return new TransactionRecord(creditAccount, LocalDateTime.now(), FundsMovement.Debit, Action.Transfer, Balance.negative(amount));
    }
}
