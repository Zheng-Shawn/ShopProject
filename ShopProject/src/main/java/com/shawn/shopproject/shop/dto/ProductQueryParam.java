package com.shawn.shopproject.shop.dto;

import com.shawn.shopproject.constant.ProductCategory;

public class ProductQueryParam {

    private ProductCategory productCategory;
    private String search;
    private String created_date;
    private String sort;
    private Integer limit;
    private Integer offset;

    public ProductQueryParam(ProductCategory productCategory,String search,String created_date,String sort,Integer limit,Integer offset){
        this.productCategory = productCategory;
        this.search = search;
        this.created_date = created_date;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
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

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }
}
