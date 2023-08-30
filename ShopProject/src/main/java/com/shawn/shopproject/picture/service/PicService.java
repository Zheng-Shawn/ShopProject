package com.shawn.shopproject.picture.service;

import com.shawn.shopproject.picture.entity.ProductPic;
import com.shawn.shopproject.picture.entity.UtilPic;

import java.util.List;

public interface PicService {



    List<byte[]> findAllProductPicByproductId(Integer productid);

    ProductPic findFirstByproductId(Integer productid);





    byte[] getUtilPic(Integer utilid);
}
