package com.wonu606.managingproducts.model.product;

import java.time.LocalDateTime;

public record CreatedAt(LocalDateTime time) {

    public CreatedAt {
        validate(time);
    }

    private static void validate(LocalDateTime value) {
        if (value == null || value.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("잘못된 CreatedAt: " + value);
        }
    }
}
