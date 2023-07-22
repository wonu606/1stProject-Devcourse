package com.wonu606.managingproducts.service.product;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.CreatedAt;
import com.wonu606.managingproducts.model.product.Price;
import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.model.product.Quantity;
import com.wonu606.managingproducts.model.product.UpdatedAt;
import com.wonu606.managingproducts.repository.product.draft.ProductInsertDraft;
import com.wonu606.managingproducts.repository.product.draft.ProductUpdateDraft;
import com.wonu606.managingproducts.repository.product.ProductRepository;
import com.wonu606.managingproducts.service.product.dto.ProductCreateRequestDto;
import com.wonu606.managingproducts.service.product.dto.ProductResponseDto;
import com.wonu606.managingproducts.service.product.dto.ProductUpdateRequestDto;
import com.wonu606.managingproducts.service.product.mapper.ProductMapper;
import com.wonu606.managingproducts.util.DateTimeProvider;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final DateTimeProvider dateTimeProvider;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository repository, DateTimeProvider dateTimeProvider) {
        this.repository = repository;
        this.dateTimeProvider = dateTimeProvider;
        this.productMapper = ProductMapper.INSTANCE;
    }

    public List<ProductResponseDto> getAllProducts() {
        List<Product> allProductList = repository.findAll();
        return allProductList.stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto createProduct(ProductCreateRequestDto requestDto) {
        LocalDateTime nowTime = dateTimeProvider.now();
        ProductInsertDraft draft = new ProductInsertDraft(new ProductName(requestDto.name()),
                Category.fromName(requestDto.category()),
                new Price(requestDto.price()),
                new Quantity(requestDto.quantity()),
                new CreatedAt(nowTime),
                new UpdatedAt(nowTime));

        Product createdProduct = repository.insert(draft);
        return productMapper.toProductResponseDto(createdProduct);
    }

    public ProductResponseDto updateProduct(ProductUpdateRequestDto requestDto) {
        ProductUpdateDraft draft = new ProductUpdateDraft(new ProductId(requestDto.id()),
                new ProductName(requestDto.name()),
                Category.fromName(requestDto.category()),
                new Price(requestDto.price()),
                new Quantity(requestDto.quantity()),
                new UpdatedAt(dateTimeProvider.now()));

        Product updatedProduct = repository.update(draft);
        return productMapper.toProductResponseDto(updatedProduct);
    }

    public Optional<ProductResponseDto> getProductsById(Long productId) {
        ProductId queryId = new ProductId(productId);

        Optional<Product> foundProductOptional = repository.findById(queryId);
        return foundProductOptional.map(productMapper::toProductResponseDto);
    }

    public List<ProductResponseDto> getProductsByName(String productName) {
        ProductName queryName = new ProductName(productName);

        return repository.findByName(queryName).stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }

    public List<ProductResponseDto> getProductsByCategory(String categoryName) {
        Category category = Category.fromName(categoryName);

        List<Product> foundProducts = repository.findByCategory(category);
        return foundProducts.stream()
                .map(productMapper::toProductResponseDto)
                .collect(Collectors.toList());
    }
}
