package com.wonu606.managingproducts.model.product;

public record Quantity(Integer value) {

    public Quantity {
        validate(value);
    }

    private static void validate(Integer value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("잘못된 Quantity: " + value);
        }
    }
}
