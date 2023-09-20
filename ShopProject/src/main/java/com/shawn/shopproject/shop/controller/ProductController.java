package com.shawn.shopproject.shop.controller;

import com.shawn.shopproject.constant.ProductCategory;
import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.dto.CartList;
import com.shawn.shopproject.shop.entity.ProductVO;
import com.shawn.shopproject.shop.service.ProductService;
import com.shawn.shopproject.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/select_product/{productId}")
    public ResponseEntity<ProductVO> getProductById(@PathVariable Integer productId) {

        ProductVO productVO = productService.getProductById(productId);

        if (productVO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productVO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getproducts")
    public ResponseEntity<Page> getProducts(//價格區間
                                            @RequestParam(required = false) @Min(0) Integer minprice,
                                            @RequestParam(required = false) @Max(2100000000) Integer maxprice,
                                            //查詢
                                            @RequestParam(required = false) ProductCategory productCategory,
                                            @RequestParam(required = false) String search,
                                            //排序
                                            @RequestParam(defaultValue = "createdDate") String orderBy,
                                            @RequestParam(defaultValue = "DESC") String sort,
                                            //分頁
                                            @RequestParam(defaultValue = "9") @Max(50) @Min(0) Integer limit,
                                            @RequestParam(defaultValue = "0") @Min(0) Integer offset) {


        //為接收請求參數初始化一個類別,增加解藕，若需修改傳入參數，修改幅度不大
        ProductQueryParam productQueryParam = new ProductQueryParam(minprice, maxprice, productCategory, search, orderBy, sort, limit, offset);

        List<ProductVO> list = productService.getProducts(productQueryParam);

        // 取得商品總筆數
        Integer total = productService.getProductsTotal(productQueryParam);
        // 取得全部類別
        List<ProductVO> allCategory = productService.getAllcategory(productQueryParam);

        //為回傳資料初始化一個類別,除了商品列表及增加回傳總筆數,加強前端分頁功能
        Page page = new Page(limit, offset, total, productCategory, search, allCategory, list);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/add_product")
    public ResponseEntity<?> addProductById(@RequestBody @Valid ProductDTO productDTO) {

        productService.addProduct(productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update_product/{productId}")
    public ResponseEntity<ProductVO> updateProduct(@PathVariable Integer productId,
                                                   @RequestBody @Valid ProductDTO productDTO) {

        ProductVO productVO = productService.getProductById(productId);

        if (productVO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete_product/{productId}")
    public ResponseEntity<?> deleteproduct(@PathVariable Integer productId) {

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/addcart")
    public ResponseEntity<?> addcart(HttpServletRequest request,
                                     @RequestBody List<Integer> cartList) {
        // 取得session 若無不另生成session
        HttpSession session = request.getSession(false);
        // 如果是訪客返回202，改用cookie存
        if (session == null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            List<Integer> cart = (List<Integer>) session.getAttribute("cart");
            cart.add(cartList.get(0));
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/updatecart")
    public ResponseEntity<?> updatecart(HttpServletRequest request,
                                     @RequestBody List<Integer> cartList) {

        HttpSession session = request.getSession(false);
        List<Integer> cart = (List<Integer>) session.getAttribute("cart");

        cart.clear();
        cart.addAll(cartList);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/getproductbycart")
    public ResponseEntity<Map<String, Object>> getProductByCart(@SessionAttribute(value = "cart", required = false) List<Integer> cartlist) {
        Map<String, Object> response = new HashMap<>();
        if (cartlist == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else {
            List<ProductVO> productlist = productService.getProductsByCart(cartlist);
            response.put("productlist", productlist);

            // 使用Stream和Collectors來回傳list每个值及數量
            Map<Integer, Integer> counts = cartlist.stream()
                    .collect(Collectors.groupingBy(e -> e, Collectors.collectingAndThen(
                            Collectors.counting(),
                            Long::intValue // 將counting()回傳的long轉換為Integer
                    )));

            List<CartList> carts = new ArrayList<>();
            // 迭代取得每个值及數量
            counts.forEach((productid, quantity) -> {
                CartList cart = new CartList();
                cart.setProductid(productid);
                cart.setQuantity(quantity);
                carts.add(cart);
            });
            response.put("cartlist", carts);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

}
