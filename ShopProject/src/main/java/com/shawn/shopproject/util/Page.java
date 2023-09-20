package com.shawn.shopproject.util;

import com.shawn.shopproject.constant.ProductCategory;
import com.shawn.shopproject.shop.entity.ProductVO;

import java.util.List;

public class Page {

    private Integer limit;
    private Integer offset;
    private Integer total;
    private ProductCategory productCategory;
    private String search;
    private List<ProductVO> allCategory;
    private List<ProductVO> list;

    public Page(Integer limit, Integer offset, Integer total, ProductCategory productCategory,String search,List<ProductVO> allCategory, List<ProductVO> list){
        this.limit = limit;
        this.offset = offset;
        this.total = total;
        this.productCategory = productCategory;
        this.search = search;
        this.allCategory = allCategory;
        this.list = list;
    }

    public Integer getLimit() {
        return limit;
    }
    public Integer getOffset() {
        return offset;
    }
    public Integer getTotal() {
        return total;
    }
    public ProductCategory getProductCategory() { return productCategory; }
    public String getSearch() { return search; }
    public List<ProductVO> getAllCategory() { return allCategory; }
    public List<ProductVO> getList() {
        return list;
    }

}
