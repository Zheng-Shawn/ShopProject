package com.shawn.shopproject.shop.service;

import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.model.ProductVO;

import java.util.List;

public interface ProductService {

    ProductVO getProductById(Integer productId);

    List<ProductVO> getproducts(ProductQueryParam productQueryParam);

    String addProduct(ProductDTO productDTO);

    void updateProduct(Integer productId , ProductDTO productDTO);

    void deleteProductById(Integer productId);



}
