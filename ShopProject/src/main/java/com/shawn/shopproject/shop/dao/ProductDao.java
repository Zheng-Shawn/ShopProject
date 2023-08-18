package com.shawn.shopproject.shop.dao;

import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.model.ProductVO;

import java.util.List;

public interface ProductDao {

    ProductVO getProductById(Integer productId);

    List<ProductVO> getProducts(ProductQueryParam productQueryParam);
    Integer getProductsTotal(ProductQueryParam productQueryParam);
    String addProduct(ProductDTO productDTO);

    void updateProduct(Integer productId ,ProductDTO productDTO);

    void deleteProductById(Integer productId);
}
