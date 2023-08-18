package com.shawn.shopproject.shop.dao;

import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.model.ProductVO;

public interface ProductDao {

    ProductVO getProductById(Integer productId);

    String addProduct(ProductDTO productDTO);

    void updateProduct(Integer productId ,ProductDTO productDTO);

    void deleteProductById(Integer productId);
}
