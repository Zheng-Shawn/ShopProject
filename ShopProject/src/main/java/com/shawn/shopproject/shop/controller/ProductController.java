package com.shawn.shopproject.shop.controller;

import com.shawn.shopproject.constant.ProductCategory;
import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.model.ProductVO;
import com.shawn.shopproject.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/select_product/{productId}")
    public ResponseEntity<ProductVO> getProductById(@PathVariable Integer productId){

        ProductVO productVO = productService.getProductById(productId);

        if(productVO != null){
            return ResponseEntity.status(HttpStatus.OK).body(productVO);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @GetMapping("/getproducts")
    public ResponseEntity<List<ProductVO>> getProducts(@RequestParam(required = false)ProductCategory productCategory,
                                                       @RequestParam(required = false)String search,
                                                       @RequestParam(defaultValue = "created_date" )String orderBy,
                                                       @RequestParam(defaultValue = "DESC")String sort,
                                                       @RequestParam(defaultValue = "5") @Max(50) @Min(0) Integer limit,
                                                       @RequestParam(defaultValue = "0") @Min(0) Integer offset){

        ProductQueryParam productQueryParam = new ProductQueryParam(productCategory,search,orderBy,sort,limit,offset);

        List<ProductVO> list = productService.getproducts(productQueryParam);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @PostMapping("/add_product")
    public  ResponseEntity<String> addProductById(@RequestBody @Valid ProductDTO productDTO){


        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productDTO));
    }

    @PutMapping("/update_product/{productId}")
    public ResponseEntity<ProductVO> updateProduct(@PathVariable Integer productId,
                                                   @RequestBody @Valid ProductDTO productDTO){

        ProductVO productVO = productService.getProductById(productId);

        if(productVO == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId,productDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete_product/{productId}")
    public ResponseEntity<?> deleteproduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }




}
