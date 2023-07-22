package com.wonu606.managingproducts.service.product.dto;

public record ProductUpdateRequestDto (Long id,
                                       String name,
                                       String category,
                                       Long price,
                                       Integer quantity){

}
