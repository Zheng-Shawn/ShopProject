package com.shawn.shopproject.picture.controller;

import com.shawn.shopproject.picture.entity.ProductPic;
import com.shawn.shopproject.picture.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PicController {

    @Autowired
    private PicService picService;


    @GetMapping("/product/picture/{productid}")
    public ResponseEntity<byte[]> getOneProductPicById(@PathVariable Integer productid) {

        // 建立 HttpHeaders 物件來設置 HTTP 回應的 headers
        HttpHeaders headers = new HttpHeaders();
        // 將 Content-Type header 設置為 IMAGE_GIF，表示我們將回傳圖片
        headers.setContentType(MediaType.IMAGE_GIF);

        // 檢查資料是否存在
        ProductPic productPic = picService.findFirstByproductId(productid);

        if (productPic != null) {
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(productPic.getProductPic());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(headers).body(picService.getUtilPic(1));
        }
    }

    @GetMapping("/product/pictures/{productid}")
    public ResponseEntity<List<byte[]>> getAllProductPicsById(@PathVariable Integer productid) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_GIF);

        List list = picService.findAllProductPicByproductId(productid);

        if (list != null) {
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(list);
        } else {
            list =  new ArrayList<>();
            list.add(picService.getUtilPic(1));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).headers(headers).body(list);
        }
    }


}
