package com.shawn.shopproject.shop.dto;

import com.shawn.shopproject.constant.ProductCategory;

public class ProductQueryParam {

    private Integer minprice;
    private Integer maxprice;
    private ProductCategory productCategory;
    private String search;
    private String orderBy;
    private String sort;
    private Integer limit;
    private Integer offset;

    public ProductQueryParam(Integer minprice,Integer maxprice,ProductCategory productCategory,String search,String orderBy,String sort,Integer limit,Integer offset){
        this.minprice = minprice;
        this.maxprice = maxprice;
        this.productCategory = productCategory;
        this.search = search;
        this.orderBy = orderBy;
        this.sort = sort;
        this.limit = limit;
        this.offset = offset;
    }

    public Integer getMinprice() { return minprice; }

    public Integer getMaxprice() { return maxprice; }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public String getSearch() {
        return search;
    }

    public String getOrderBy() { return orderBy; }

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
