package com.wonu606.managingproducts.service.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.CreatedAt;
import com.wonu606.managingproducts.model.product.Price;
import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.model.product.Quantity;
import com.wonu606.managingproducts.model.product.UpdatedAt;
import com.wonu606.managingproducts.repository.draft.ProductInsertDraft;
import com.wonu606.managingproducts.repository.draft.ProductUpdateDraft;
import com.wonu606.managingproducts.repository.product.ProductRepository;
import com.wonu606.managingproducts.service.product.dto.ProductCreateRequestDto;
import com.wonu606.managingproducts.service.product.dto.ProductResponseDto;
import com.wonu606.managingproducts.service.product.dto.ProductUpdateRequestDto;
import com.wonu606.managingproducts.service.product.mapper.ProductMapper;
import com.wonu606.managingproducts.util.DateTimeProvider;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProductServiceTest {

    private ProductRepository repository;

    private DateTimeProvider dateTimeProvider;

    private ProductMapper productMapper;

    private ProductService productService;

    @BeforeAll
    void setup() {
        repository = mock(ProductRepository.class);
        dateTimeProvider = mock(DateTimeProvider.class);
        productMapper = ProductMapper.INSTANCE;
        productService = new ProductService(repository, dateTimeProvider);
    }

    @ParameterizedTest
    @MethodSource("com.wonu606.managingproducts.fixture.ProductFixture#validProductsProvider")
    void getAllProducts_WhenProductsExist_ReturnsListOfProductResponseDto(Product product) {
        // Given
        ProductResponseDto responseDto = productMapper.toProductResponseDto(product);
        given(repository.findAll()).willReturn(Collections.singletonList(product));

        // When
        List<ProductResponseDto> actualProductList = productService.getAllProducts();

        // Then
        assertThat(actualProductList).hasSize(1);
        assertThat(actualProductList).usingRecursiveFieldByFieldElementComparator()
                .contains(responseDto);
    }

    @Test
    void createProduct_WithValidRequestDto_ReturnsProductResponseDto() {
        // Given
        ProductCreateRequestDto requestDto = new ProductCreateRequestDto("name",
                "coffee",
                100L,
                10);
        LocalDateTime nowTime = LocalDateTime.now();
        given(dateTimeProvider.now()).willReturn(nowTime);

        // When
        productService.createProduct(requestDto);

        // Then
        ProductInsertDraft draft = new ProductInsertDraft(new ProductName(requestDto.name()),
                Category.fromName(requestDto.category()),
                new Price(requestDto.price()),
                new Quantity(requestDto.quantity()),
                new CreatedAt(nowTime),
                new UpdatedAt(nowTime));
        verify(repository, times(1)).insert(draft);
    }

    @Test
    void updateProduct_WithValidRequestDto_ReturnsUpdatedProductResponseDto() {
        // Given
        ProductUpdateRequestDto requestDto = new ProductUpdateRequestDto(1L, "name", "coffee", 100L,
                10);
        LocalDateTime nowTime = LocalDateTime.now();
        given(dateTimeProvider.now()).willReturn(nowTime);

        // When
        productService.updateProduct(requestDto);

        // Then
        ProductUpdateDraft draft = new ProductUpdateDraft(new ProductId(requestDto.id()),
                new ProductName(requestDto.name()),
                Category.fromName(requestDto.category()),
                new Price(requestDto.price()),
                new Quantity(requestDto.quantity()),
                new UpdatedAt(nowTime));
        verify(repository, times(1)).update(draft);
    }

    @Test
    void getProductsById_WhenProductExists_ReturnsProductResponseDto() {
        // Given
        Long productId = 1L;

        // When
        productService.getProductsById(productId);

        // Then
        verify(repository, times(1)).findById(new ProductId(productId));
    }

    @Test
    void getProductsByName_WhenProductsExist_ReturnsListOfProductResponseDto() {
        // Given
        String productName = "name";

        // When
        productService.getProductsByName(productName);

        // Then
        verify(repository, times(1)).findByName(new ProductName(productName));
    }

    @Test
    void getProductsByCategory_WhenProductsExist_ReturnsListOfProductResponseDto() {
        // Given
        String categoryName = "coffee";

        // When
        productService.getProductsByCategory(categoryName);

        // Then
        verify(repository, times(1)).findByCategory(Category.fromName(categoryName));
    }
}
