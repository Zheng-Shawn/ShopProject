package com.shawn.shopproject.controller;

import com.shawn.shopproject.model.ProductVO;
import com.shawn.shopproject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductVO> getProductById(@PathVariable Integer productId){

        ProductVO productVO = productService.getProductById(productId);

        if(productVO != null){
            return ResponseEntity.status(HttpStatus.OK).body(productVO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }


}
