package com.shawn.shopproject.picture.service.impl;

import com.shawn.shopproject.picture.dao.ProductRepository;
import com.shawn.shopproject.picture.dao.UtilRepository;
import com.shawn.shopproject.picture.entity.ProductPic;
import com.shawn.shopproject.picture.entity.UtilPic;
import com.shawn.shopproject.picture.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class PicServiceimpl implements PicService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UtilRepository utilRepository;


    @Override
    public List<byte[]> findAllProductPicByproductId(Integer productid) {

        // 將Iterable可迭代對象 轉換為List 實體集合
        Iterable<ProductPic> productPics = productRepository.findAllByproductId(productid);
        List<ProductPic> VOlist = StreamSupport.stream(productPics.spliterator(), false).collect(Collectors.toList());
        // 轉換成List byte[]集合
        List<byte[]> list = new ArrayList<>();
        // 檢查是否為空對象
        if (!VOlist.isEmpty()) {
            for (int i = 0; i < VOlist.size(); i++) {
                list.add(VOlist.get(i).getProductPic());
            }
            return list;
        } else {
            return null;
        }
    }

    @Override
    public ProductPic findFirstByproductId(Integer productid) {

        Optional<ProductPic> productPic = productRepository.findFirstByproductId(productid);

        if (productPic.isPresent()) {
            return productPic.get();
        } else {
            return null;
        }
    }


    @Override
    public byte[] getUtilPic(Integer utilid) {

        UtilPic utilPic = utilRepository.findById(utilid).orElse(null);

        return utilPic.getUtilPic();
    }
}
