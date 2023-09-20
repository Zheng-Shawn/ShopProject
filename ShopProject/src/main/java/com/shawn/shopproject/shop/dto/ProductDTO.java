package com.shawn.shopproject.shop.dto;

import com.shawn.shopproject.constant.ProductCategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ProductDTO {

    @NotBlank(message = "產品名稱不能為空")
    private String product_name;
    private ProductCategory category;
    @NotNull(message = "售價不能為空")
    private Integer price;

    private Integer sold;
    @NotBlank(message = "商品描述不能為空")
    private String description;
    private String brand;

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


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getSold() {
        return sold;
    }

    public void setsold(Integer stock) {
        this.sold = sold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
}
