package com.shawn.shopproject.picture.entity;

import javax.persistence.*;

@Table(name = "productpic")
@Entity
public class ProductPic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productPicId")
    private Integer productPicId;
    @Column(name = "productId")
    private Integer productId;
    @Column(name = "productPic")
    private byte[] productPic;

    public Integer getProductPicId() {
        return productPicId;
    }

    public void setProductPicId(Integer productPicId) {
        this.productPicId = productPicId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public byte[] getProductPic() {
        return productPic;
    }

    public void setProductPic(byte[] productPic) {
        this.productPic = productPic;
    }
}
