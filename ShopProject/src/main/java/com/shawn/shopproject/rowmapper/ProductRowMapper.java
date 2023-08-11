package com.shawn.shopproject.rowmapper;

import com.shawn.shopproject.model.ProductVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductVO>{


    @Override
    public ProductVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductVO productVO = new ProductVO();

        productVO.setProduct_id(resultSet.getInt("product_id"));
        productVO.setProduct_name(resultSet.getString("product_name"));
        productVO.setCategory(resultSet.getString("category"));
        productVO.setImage_url(resultSet.getString("image_url"));
        productVO.setPrice(resultSet.getInt("price"));
        productVO.setStock(resultSet.getInt("stock"));
        productVO.setDescription(resultSet.getString("description"));
        productVO.setCreated_date(resultSet.getDate("created_date"));
        productVO.setLast_modified_date(resultSet.getDate("last_modified_date"));

        return productVO;
    }
}
