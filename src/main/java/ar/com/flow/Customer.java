package ar.com.flow;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class Customer {
    private final String name;

    public static Customer named(String name) {
        return new Customer(name);
    }
}
