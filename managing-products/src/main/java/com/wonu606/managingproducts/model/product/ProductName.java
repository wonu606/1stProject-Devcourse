package com.wonu606.managingproducts.model.product;

public record ProductName(String value) {

    public ProductName {
        validate(value);
    }

    private static void validate(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("잘못된 ProductName: " + value);
        }
    }
}
