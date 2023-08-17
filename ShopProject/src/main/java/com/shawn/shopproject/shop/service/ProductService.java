package com.shawn.shopproject.shop.service;

import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.model.ProductVO;

public interface ProductService {

    ProductVO getProductById(Integer productId);

    String addProduct(ProductDTO productDTO);

    void updateProduct(Integer productId , ProductDTO productDTO);

}
