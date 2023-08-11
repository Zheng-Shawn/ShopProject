package com.shawn.shopproject.dao;

import com.shawn.shopproject.model.ProductVO;

public interface ProductDao {

    ProductVO getProductById(Integer productId);

}
