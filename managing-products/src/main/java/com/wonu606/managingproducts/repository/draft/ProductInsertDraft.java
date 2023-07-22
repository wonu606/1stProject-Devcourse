package com.wonu606.managingproducts.repository.draft;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.CreatedAt;
import com.wonu606.managingproducts.model.product.Price;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.model.product.Quantity;
import com.wonu606.managingproducts.model.product.UpdatedAt;

public record ProductInsertDraft(ProductName name,
                                 Category category,
                                 Price price,
                                 Quantity quantity,
                                 CreatedAt createdAt,
                                 UpdatedAt updatedAt) {

}
