package com.wonu606.managingproducts.repository.product;

import com.wonu606.managingproducts.model.product.Category;
import com.wonu606.managingproducts.model.product.Product;
import com.wonu606.managingproducts.model.product.ProductId;
import com.wonu606.managingproducts.model.product.ProductName;
import com.wonu606.managingproducts.repository.product.draft.ProductInsertDraft;
import com.wonu606.managingproducts.repository.product.draft.ProductUpdateDraft;
import com.wonu606.managingproducts.repository.product.rowmapper.ProductRowMapper;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductJdbcRepository implements ProductRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final ProductRowMapper rowMapper;

    public ProductJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate,
            ProductRowMapper rowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<Product> findAll() {
        String selectSql = "SELECT product_id, product_name, category, price, quantity, created_at, updated_at FROM products";

        return jdbcTemplate.query(selectSql, rowMapper);
    }

    @Override
    public Product insert(ProductInsertDraft draft) {
        String insertSql = "INSERT INTO products (product_name, category, price, quantity, created_at, updated_at) VALUES (:product_name, :category, :price, :quantity, :created_at, :updated_at)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("product_name", draft.name().value())
                .addValue("category", draft.category().name())
                .addValue("price", draft.price().value())
                .addValue("quantity", draft.quantity().value())
                .addValue("created_at", Timestamp.valueOf(draft.createdAt().time()))
                .addValue("updated_at", Timestamp.valueOf(draft.updatedAt().time()));
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(insertSql, params, keyHolder, new String[] {"product_id"});
        Number key = keyHolder.getKey();
        if (key == null) {
            throw new RuntimeException("키 생성에 실패했습니다.");
        }
        return findById(new ProductId(key.longValue()))
                .orElseThrow(() -> new RuntimeException("데이터베이스에 저장되지 못했습니다."));
    }

    @Override
    public Product update(ProductUpdateDraft draft) {
        String updateSql = "UPDATE products SET product_name = :product_name, category = :category, price = :price, quantity = :quantity, updated_at = :updated_at WHERE product_id = :product_id";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("product_id", draft.id().value())
                .addValue("product_name", draft.name().value())
                .addValue("category", draft.category().name())
                .addValue("price", draft.price().value())
                .addValue("quantity", draft.quantity().value())
                .addValue("updated_at", Timestamp.valueOf(draft.updatedAt().time()));

        jdbcTemplate.update(updateSql, parameters);
        return findById(draft.id())
                .orElseThrow(() -> new RuntimeException("업데이트된 데이터를 찾지 못했습니다."));
    }

    @Override
    public Optional<Product> findById(ProductId productId) {
        String findSql = "SELECT product_id, product_name, category, price, quantity, created_at, updated_at FROM products WHERE product_id = :product_id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("product_id", productId.value());

        List<Product> foundProductList = jdbcTemplate.query(findSql, params, rowMapper);
        if (foundProductList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(foundProductList.get(0));
    }

    @Override
    public List<Product> findByName(ProductName productName) {
        String findSql = "SELECT product_id, product_name, category, price, quantity, created_at, updated_at FROM products WHERE product_name = :product_name";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("product_name", productName.value());

        return jdbcTemplate.query(findSql, params, rowMapper);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        String findSql = "SELECT product_id, product_name, category, price, quantity, created_at, updated_at FROM products WHERE category = :category";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("category", category.name());

        return jdbcTemplate.query(findSql, params, rowMapper);
    }
}
