package com.shawn.shopproject.picture.dao;

import com.shawn.shopproject.picture.entity.ProductPic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ProductRepository extends CrudRepository<ProductPic,Integer> {


    Iterable<ProductPic> findAllByproductId(Integer productid);
    Optional<ProductPic> findFirstByproductId(Integer productid);

}
