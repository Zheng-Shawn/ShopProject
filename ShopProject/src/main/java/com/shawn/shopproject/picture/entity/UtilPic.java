package com.shawn.shopproject.picture.entity;


import javax.persistence.*;

@Table(name = "utilpic")
@Entity
public class UtilPic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "utilPicId")
    private Integer utilPicId;

    @Column(name = "utilPic")
    private byte[] utilPic;

    public Integer getUtilPicId() {
        return utilPicId;
    }

    public void setUtilPicId(Integer utilPicId) {
        this.utilPicId = utilPicId;
    }

    public byte[] getUtilPic() {
        return utilPic;
    }

    public void setUtilPic(byte[] utilPic) {
        this.utilPic = utilPic;
    }
}
