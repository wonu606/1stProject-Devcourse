package com.wonu606.managingproducts.model.product;

public record ProductId(Long value) {

    public ProductId {
        validate(value);
    }

    private static void validate(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("잘못된 ProductId: " + id);
        }
    }
}
