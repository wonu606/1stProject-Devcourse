package com.wonu606.managingproducts.service.product.mapper;

import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.service.product.dto.ProductResponseDto;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-23T00:16:47+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponseDto toProductResponseDto(Product product) {
        if ( product == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String category = null;
        Long price = null;
        Integer quantity = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = product.getId();
        name = product.getName();
        category = product.getCategory();
        price = product.getPrice();
        quantity = product.getQuantity();
        createdAt = product.getCreatedAt();
        updatedAt = product.getUpdatedAt();

        ProductResponseDto productResponseDto = new ProductResponseDto( id, name, category, price, quantity, createdAt, updatedAt );

        return productResponseDto;
    }
}
