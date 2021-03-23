package com.emilianomenendez.veritran;

public class Customer {
    private final String name;

    public static Customer named(String name) {
        return new Customer(name);
    }

    public Customer(String name) {
        this.name = name;
    }
}
