package com.shawn.shopproject.shop.rowmapper;

import com.shawn.shopproject.constant.ProductCategory;
import com.shawn.shopproject.shop.model.ProductVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductVO>{


    @Override
    public ProductVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductVO productVO = new ProductVO();

        productVO.setProduct_id(resultSet.getInt("product_id"));
        productVO.setProduct_name(resultSet.getString("product_name"));

//        String to Enum 字串轉成Enum類型
        String categoryStr = resultSet.getString("category");
        ProductCategory productCategory = ProductCategory.valueOf(categoryStr);
        productVO.setCategory(productCategory);


        productVO.setImage_url(resultSet.getString("image_url"));
        productVO.setPrice(resultSet.getInt("price"));
        productVO.setStock(resultSet.getInt("stock"));
        productVO.setDescription(resultSet.getString("description"));
        productVO.setCreated_date(resultSet.getTimestamp("created_date"));
        productVO.setLast_modified_date(resultSet.getTimestamp("last_modified_date"));

        return productVO;
    }
}
