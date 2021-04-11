package ar.com.flow.bankaccount.transaction;

public interface TransactionLog {
    void add(TransactionRecord record);
}
