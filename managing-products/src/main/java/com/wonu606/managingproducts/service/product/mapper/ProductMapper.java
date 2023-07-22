package com.wonu606.managingproducts.service.product.mapper;

import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.service.product.dto.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductResponseDto toProductResponseDto(Product product);
}
