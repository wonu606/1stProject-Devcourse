package com.wonu606.managingproducts.model.product;

import java.util.Arrays;
import java.util.Objects;

public enum Category {
    COFFEE;

    public static Category fromName(String categoryName) {
        return Arrays.stream(values())
                .filter(value -> Objects.equals(value.name(), categoryName.toUpperCase()))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("존재하지 않는 카테고리 이름: " + categoryName));
    }
}
