package com.shawn.shopproject.util;

import com.shawn.shopproject.shop.model.ProductVO;

import java.util.List;

public class Page {

    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<ProductVO> list;

    public Page(Integer limit,Integer offset,Integer total,List<ProductVO> list){
        this.limit = limit;
        this.offset = offset;
        this.total = total;
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

    public List<ProductVO> getList() {
        return list;
    }

}
