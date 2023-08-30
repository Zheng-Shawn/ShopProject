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

    public ProductPic setProductPicId(Integer productPicId) {
        this.productPicId = productPicId;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public ProductPic setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public byte[] getProductPic() {
        return productPic;
    }

    public ProductPic setProductPic(byte[] productPic) {
        this.productPic = productPic;
        return this;
    }
}
