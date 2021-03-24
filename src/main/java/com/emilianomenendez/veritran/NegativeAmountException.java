package com.emilianomenendez.veritran;

public class NegativeAmountException extends RuntimeException {
    public NegativeAmountException(int amount) {
        super("Negative Amount: " + amount);
    }
}
