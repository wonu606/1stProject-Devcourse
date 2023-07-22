package com.wonu606.managingproducts.service.product.dto;

public record ProductCreateRequestDto(String name,
                                      String category,
                                      Long price,
                                      Integer quantity) {

}
