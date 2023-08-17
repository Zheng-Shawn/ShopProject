package com.shawn.shopproject.shop.dto;

import com.shawn.shopproject.constant.ProductCategory;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ProductDTO {

    @NotBlank(message = "產品名稱不能為空")
    private String product_name;
    private ProductCategory category;
    @NotBlank(message = "圖片不能為空")
    private String image_url;
    @NotNull(message = "售價不能為空")
    private Integer price;
    @NotNull(message = "庫存不能為空")
    private Integer stock;
    @NotBlank(message = "商品描述不能為空")
    private String description;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }




}
