package com.shawn.shopproject.shop.dto;

public class CartList {

    private Integer productid;
    private Integer quantity;

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getProductid() {
        return productid;
    }


    public Integer getQuantity() {
        return quantity;
    }


}
