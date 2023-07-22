package com.wonu606.managingproducts.repository.product.draft;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.Price;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.model.product.Quantity;
import com.wonu606.managingproducts.model.product.UpdatedAt;

public record ProductUpdateDraft(ProductId id,
                                 ProductName name,
                                 Category category,
                                 Price price,
                                 Quantity quantity,
                                 UpdatedAt updatedAt) {

}
