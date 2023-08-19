package com.shawn.shopproject.shop.controller;

import com.shawn.shopproject.constant.ProductCategory;
import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.model.ProductVO;
import com.shawn.shopproject.shop.service.ProductService;
import com.shawn.shopproject.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Page> getProducts(//查詢
                                            @RequestParam(required = false)ProductCategory productCategory,
                                            @RequestParam(required = false)String search,
                                            //排序
                                            @RequestParam(defaultValue = "created_date" )String orderBy,
                                            @RequestParam(defaultValue = "DESC")String sort,
                                            //分頁
                                            @RequestParam(defaultValue = "10") @Max(50) @Min(0) Integer limit,
                                            @RequestParam(defaultValue = "0") @Min(0) Integer offset){

        //為接收請求參數初始化一個類別,加強解藕姓，若需修改傳入參數，修改幅度不大
        ProductQueryParam productQueryParam = new ProductQueryParam(productCategory,search,orderBy,sort,limit,offset);

        List<ProductVO> list = productService.getProducts(productQueryParam);
        Integer total = productService.getProductsTotal(productQueryParam);

        //為回傳資料初始化一個類別,除了商品列表及增加回傳總筆數,加強前端分頁功能
        Page page = new Page(limit,offset,total,list);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/add_product")
    public  ResponseEntity<?> addProductById(@RequestBody @Valid ProductDTO productDTO){

        productService.addProduct(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
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
