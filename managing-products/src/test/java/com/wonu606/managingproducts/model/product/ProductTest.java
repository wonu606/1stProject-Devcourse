package com.wonu606.managingproducts.model.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTest {

    @ParameterizedTest
    @MethodSource("com.wonu606.managingproducts.fixture.ProductFixture#validProductArgumentsProvider")
    void testProductCreation(ProductId id, ProductName name, Category category, Price price,
            Quantity quantity, CreatedAt createdAt, UpdatedAt updatedAt) {
        // When
        Product product = new Product(id, name, category, price, quantity, createdAt, updatedAt);

        // Then
        assertThat(product.getId()).isEqualTo(id.value());
        assertThat(product.getName()).isEqualTo(name.value());
        assertThat(product.getCategory()).isEqualTo(category.name());
        assertThat(product.getPrice()).isEqualTo(price.value());
        assertThat(product.getQuantity()).isEqualTo(quantity.value());
        assertThat(product.getCreatedAt()).isEqualTo(createdAt.time());
        assertThat(product.getUpdatedAt()).isEqualTo(updatedAt.time());
    }
}
