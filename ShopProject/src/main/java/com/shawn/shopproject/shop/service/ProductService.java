package com.shawn.shopproject.shop.service;

import com.shawn.shopproject.account.entity.Member;
import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.dto.CartList;
import com.shawn.shopproject.shop.entity.ProductVO;

import java.util.List;

public interface ProductService {

    ProductVO getProductById(Integer productId);

    List<ProductVO> getProducts(ProductQueryParam productQueryParam);

    Integer getProductsTotal(ProductQueryParam productQueryParam);

    void addProduct(ProductDTO productDTO);

    void updateProduct(Integer productId, ProductDTO productDTO);

    void deleteProductById(Integer productId);

    List<ProductVO> getAllcategory(ProductQueryParam productQueryParam);

    List<CartList> getProductsByMemberId(Integer memberid);

    void storeCart(Member member,List<Integer> cartlist);

    List<ProductVO> getProductsByCart(List<Integer> list);
}
