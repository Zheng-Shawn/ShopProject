package com.shawn.shopproject.shop.service.impl;

import com.shawn.shopproject.shop.dao.ProductDao;
import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.model.ProductVO;
import com.shawn.shopproject.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ProductServiceimpl implements ProductService {


    @Autowired
    private ProductDao productDao;

    @Override
    public ProductVO getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public List<ProductVO> getProducts(ProductQueryParam productQueryParam) {
        return productDao.getProducts(productQueryParam);
    }

    @Override
    public Integer getProductsTotal(ProductQueryParam productQueryParam) {
        return productDao.getProductsTotal(productQueryParam);
    }

    @Override
    public String addProduct(ProductDTO productDTO) {
        return productDao.addProduct(productDTO);
    }

    @Override
    public void updateProduct(Integer productId ,ProductDTO productDTO) {
        productDao.updateProduct(productId,productDTO);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
