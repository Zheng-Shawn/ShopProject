package com.shawn.shopproject.shop.rowmapper;

import com.shawn.shopproject.constant.ProductCategory;
import com.shawn.shopproject.shop.entity.ProductVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductVO>{


    @Override
    public ProductVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductVO productVO = new ProductVO();

        productVO.setProduct_id(resultSet.getInt("productId"));
        productVO.setProduct_name(resultSet.getString("productName"));

        //    String to Enum 字串轉成Enum類型
        String categoryStr = resultSet.getString("category");
        ProductCategory productCategory = ProductCategory.valueOf(categoryStr);
        productVO.setCategory(productCategory);

        productVO.setPrice(resultSet.getInt("price"));
        productVO.setSold(resultSet.getInt("sold"));
        productVO.setDescription(resultSet.getString("description"));
        productVO.setCreated_date(resultSet.getTimestamp("createdDate"));
        productVO.setLast_modified_date(resultSet.getTimestamp("lastModifiedDate"));
        productVO.setBrand(resultSet.getString("brand"));

        return productVO;
    }
}
