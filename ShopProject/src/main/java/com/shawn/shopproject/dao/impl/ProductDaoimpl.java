package com.shawn.shopproject.dao.impl;

import com.shawn.shopproject.dao.ProductDao;
import com.shawn.shopproject.model.ProductVO;
import com.shawn.shopproject.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoimpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public ProductVO getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name,category,image_url,price,stock,description,created_date,last_modified_date " +
                "FROM product " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<ProductVO> productVOList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productVOList.size() > 0){
            return productVOList.get(0);
        }else {
            return null;
        }
    }
}
