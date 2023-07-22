package com.wonu606.managingproducts.model.product;

public record Price(Long value) {

    public Price {
        validate(value);
    }

    private static void validate(Long value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("잘못된 Price: " + value);
        }
    }
}
