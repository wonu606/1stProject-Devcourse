package com.wonu606.managingproducts.repository.product;

import static org.assertj.core.api.Assertions.assertThat;

import com.wonu606.managingproducts.fixture.TestDateTimeProvider;
import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.CreatedAt;
import com.wonu606.managingproducts.model.product.Price;
import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.model.product.Quantity;
import com.wonu606.managingproducts.model.product.UpdatedAt;
import com.wonu606.managingproducts.repository.product.draft.ProductInsertDraft;
import com.wonu606.managingproducts.repository.product.rowmapper.ProductRowMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@JdbcTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductJdbcRepositoryTest {

    private final ProductJdbcRepository repository;

    @Autowired
    public ProductJdbcRepositoryTest(NamedParameterJdbcTemplate jdbcTemplate) {
        repository = new ProductJdbcRepository(jdbcTemplate, new ProductRowMapper());
    }


    @BeforeAll
    void setup() {
        insertTestProducts();
    }

    private void insertTestProducts() {
        validProductInsertDraftsProvider()
                .map(Arguments::get)
                .map(objects -> (ProductInsertDraft) objects[0])
                .forEach(repository::insert);
    }

    @ParameterizedTest
    @MethodSource("validProductInsertDraftsProvider")
    void findAll_WhenProductsExist_ReturnsProducts(ProductInsertDraft expectedDraft) {
        // Given
        Product expectedProduct = new Product(
                new ProductId(42L),
                expectedDraft.name(),
                expectedDraft.category(),
                expectedDraft.price(),
                expectedDraft.quantity(),
                expectedDraft.createdAt(),
                expectedDraft.updatedAt());

        // When
        List<Product> actualProductList = repository.findAll();

        // Then
        assertThat(actualProductList).hasSize(3);
        assertThat(actualProductList).usingElementComparatorIgnoringFields("id")
                .contains(expectedProduct);
    }

    @ParameterizedTest
    @MethodSource("com.wonu606.managingproducts.fixture.ProductFixture#validProductsProvider")
    void findById_WhenProductExists_ReturnsProduct(Product expectedProduct) {
        // When
        Optional<Product> actualProductOptional = repository.findById(
                new ProductId(expectedProduct.getId()));

        // Then
        assertThat(actualProductOptional).isPresent();
        assertThat(actualProductOptional.get()).isEqualTo(expectedProduct);
    }

    @Test
    void findById_WhenProductNotExists_ReturnsEmpty() {
        // Given
        ProductId nonExistentId = new ProductId(9999L);

        // When
        Optional<Product> actualProductOptional = repository.findById(nonExistentId);

        // Then
        assertThat(actualProductOptional).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("com.wonu606.managingproducts.fixture.ProductFixture#validProductsProvider")
    void findByName_WhenProductExists_ReturnsProducts(Product expectedProduct) {
        // Given
        ProductName expectedProductName = new ProductName(expectedProduct.getName());

        // When
        List<Product> actualProductList = repository.findByName(expectedProductName);

        // Then
        assertThat(actualProductList).allMatch(actualProduct ->
                        Objects.equals(actualProduct.getName(), expectedProductName.value()));
    }

    @ParameterizedTest
    @MethodSource("com.wonu606.managingproducts.fixture.ProductFixture#validProductsProvider")
    void findByCategory_WhenProductExists_ReturnsProducts(Product expectedProduct) {
        // Given
        Category expectedCategory = Category.fromName(expectedProduct.getCategory());

        // When
        List<Product> actualProductList = repository.findByCategory(expectedCategory);

        // Then
        assertThat(actualProductList).allMatch(actualProduct ->
                Objects.equals(actualProduct.getCategory(), expectedCategory.name()));
    }

    private static Stream<Arguments> validProductInsertDraftsProvider() {
        LocalDateTime now = TestDateTimeProvider.FIXED_DATE_TIME;
        return Stream.of(
                Arguments.of(new ProductInsertDraft(
                        new ProductName("Test Product 1"), Category.COFFEE,
                        new Price(100L), new Quantity(10), new CreatedAt(now), new UpdatedAt(now))),
                Arguments.of(new ProductInsertDraft(
                        new ProductName("Test Product 2"), Category.COFFEE,
                        new Price(200L), new Quantity(20), new CreatedAt(now), new UpdatedAt(now))),
                Arguments.of(new ProductInsertDraft(
                        new ProductName("Test Product 3"), Category.COFFEE,
                        new Price(300L), new Quantity(30), new CreatedAt(now), new UpdatedAt(now)))
        );
    }
}
