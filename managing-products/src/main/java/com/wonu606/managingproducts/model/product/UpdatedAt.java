package com.wonu606.managingproducts.model.product;

import java.time.LocalDateTime;

public record UpdatedAt(LocalDateTime time) {

    public UpdatedAt {
        validate(time);
    }

    private static void validate(LocalDateTime value) {
        if (value == null || value.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("CreatedAt: " + value);
        }
    }
}
