package com.wonu606.managingproducts.fixture;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.CreatedAt;
import com.wonu606.managingproducts.model.product.Price;
import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.model.product.Quantity;
import com.wonu606.managingproducts.model.product.UpdatedAt;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class ProductFixture {

    public static Stream<Arguments> validProductArgumentsProvider() {
        LocalDateTime now = LocalDateTime.now();
        return Stream.of(
                Arguments.of(new ProductId(1L), new ProductName("Test Product 1"), Category.COFFEE,
                        new Price(100L), new Quantity(10), new CreatedAt(now), new UpdatedAt(now)),
                Arguments.of(new ProductId(2L), new ProductName("Test Product 2"), Category.COFFEE,
                        new Price(200L), new Quantity(20), new CreatedAt(now), new UpdatedAt(now)),
                Arguments.of(new ProductId(3L), new ProductName("Test Product 3"), Category.COFFEE,
                        new Price(300L), new Quantity(30), new CreatedAt(now), new UpdatedAt(now))
        );
    }

    public static Stream<Arguments> validProductsProvider() {
        LocalDateTime now = LocalDateTime.now();
        return Stream.of(
                Arguments.of(new Product(
                        new ProductId(1L), new ProductName("Test Product 1"), Category.COFFEE,
                        new Price(100L), new Quantity(10), new CreatedAt(now), new UpdatedAt(now))),
                Arguments.of(new Product(
                        new ProductId(2L), new ProductName("Test Product 2"), Category.COFFEE,
                        new Price(200L), new Quantity(20), new CreatedAt(now), new UpdatedAt(now))),
                Arguments.of(new Product(
                        new ProductId(3L), new ProductName("Test Product 3"), Category.COFFEE,
                        new Price(300L), new Quantity(30), new CreatedAt(now), new UpdatedAt(now)))
        );
    }
}
