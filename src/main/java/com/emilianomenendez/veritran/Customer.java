package com.emilianomenendez.veritran;

import java.util.Objects;

public class Customer {
    private final String name;

    public static Customer named(String name) {
        return new Customer(name);
    }

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return name.equals(customer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
