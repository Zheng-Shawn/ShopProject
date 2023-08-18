package com.shawn.shopproject.shop.dto;

import com.shawn.shopproject.constant.ProductCategory;

public class ProductQueryParam {

    private ProductCategory productCategory;
    private String search;
    private String created_date;
    private String sort;

    public ProductQueryParam(ProductCategory productCategory,String search,String created_date,String sort){
        this.productCategory = productCategory;
        this.search = search;
        this.created_date = created_date;
        this.sort = sort;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public String getSearch() {
        return search;
    }

    public String getCreated_date() {
        return created_date;
    }

    public String getSort() {
        return sort;
    }

}
