package com.wonu606.managingproducts.repository.product.rowmapper;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.CreatedAt;
import com.wonu606.managingproducts.model.product.Price;
import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.model.product.Quantity;
import com.wonu606.managingproducts.model.product.UpdatedAt;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                new ProductId(rs.getLong("product_id")),
                new ProductName(rs.getString("product_name")),
                Category.fromName(rs.getString("category")),
                new Price(rs.getLong("price")),
                new Quantity(rs.getInt("quantity")),
                new CreatedAt(rs.getTimestamp("created_at").toLocalDateTime()),
                new UpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
        );
    }
}
