package com.shawn.shopproject.service.impl;

import com.shawn.shopproject.dao.ProductDao;
import com.shawn.shopproject.model.ProductVO;
import com.shawn.shopproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductServiceimpl implements ProductService {


    @Autowired
    private ProductDao productDao;

    @Override
    public ProductVO getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
