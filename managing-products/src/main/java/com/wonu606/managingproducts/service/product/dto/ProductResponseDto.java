package com.wonu606.managingproducts.service.product.dto;

import java.time.LocalDateTime;

public record ProductResponseDto(Long id,
                                 String name,
                                 String category,
                                 Long price,
                                 Integer quantity,
                                 LocalDateTime createdAt,
                                 LocalDateTime updatedAt) {

}
