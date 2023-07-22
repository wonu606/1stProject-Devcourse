package com.wonu606.managingproducts.model.product;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Product {

    private final ProductId id;
    private final ProductName name;
    private final Category category;
    private final Price price;
    private final Quantity quantity;
    private final CreatedAt createdAt;
    private final UpdatedAt updatedAt;

    public Product(ProductId id,
            ProductName name,
            Category category,
            Price price,
            Quantity quantity,
            CreatedAt createdAt,
            UpdatedAt updatedAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id.value();
    }

    public String getName() {
        return name.value();
    }

    public String getCategory() {
        return category.name();
    }

    public Long getPrice() {
        return price.value();
    }

    public Integer getQuantity() {
        return quantity.value();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt.value();
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt.value();
    }
}
