package com.shawn.shopproject.shop.rowmapper;

import com.shawn.shopproject.constant.ProductCategory;
import com.shawn.shopproject.shop.entity.ProductVO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<ProductVO> {


    @Override
    public ProductVO mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductVO productVO = new ProductVO();

        //    String to Enum 字串轉成Enum類型
        String categoryStr = resultSet.getString("category");
        ProductCategory productCategory = ProductCategory.valueOf(categoryStr);
        productVO.setCategory(productCategory);

        return productVO;
    };


}