package com.wonu606.managingproducts.repository.product;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.repository.draft.ProductInsertDraft;
import com.wonu606.managingproducts.repository.draft.ProductUpdateDraft;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Product insert(ProductInsertDraft draft);

    Product update(ProductUpdateDraft draft);

    Optional<Product> findById(ProductId productId);

    List<Product> findByName(ProductName productName);

    List<Product> findByCategory(Category category);
}
