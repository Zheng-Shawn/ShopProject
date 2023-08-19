package com.shawn.shopproject.shop.dao.impl;

import com.shawn.shopproject.shop.dao.ProductDao;
import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.model.ProductVO;
import com.shawn.shopproject.shop.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoimpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public ProductVO getProductById(Integer productId) {
        String sql = "SELECT product_id,product_name,category,image_url,price,stock,description,created_date,last_modified_date " +
                "FROM product " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<ProductVO> productVOList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if (productVOList.size() > 0) {
            return productVOList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<ProductVO> getProducts(ProductQueryParam productQueryParam) {
        String sql = "SELECT * FROM product WHERE 1=1 ";
        //查全部或帶參數的查詢  因1=1對查詢結果不影響，但為了拼接sql變數，而要使用AND連接

        Map<String,Object> map = new HashMap<>();

        //查詢條件
        sql = getproductFilter(sql,map,productQueryParam);

        //排序條件
        sql += " ORDER BY " + productQueryParam.getCreated_date() + " " + productQueryParam.getSort();

        //分頁功能 限制查詢筆數
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParam.getLimit());
        map.put("offset",productQueryParam.getOffset());

        List<ProductVO> list = namedParameterJdbcTemplate.query(sql,map,new ProductRowMapper());

        return list;
    }

    @Override
    public Integer getProductsTotal(ProductQueryParam productQueryParam) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";

        HashMap<String,Object> map = new HashMap<>();

        sql = getproductFilter(sql,map,productQueryParam);

        //當要取得非實體的值,寫法會稍微不同於query()
       Integer total = namedParameterJdbcTemplate.queryForObject(sql,map,Integer.class);

        return total;
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        String sql = "INSERT INTO product(product_name,category,image_url,price,stock,description,created_date,last_modified_date)" +
                "VALUES (:product_name,:category,:image_url,:price,stock,:description,:created_date,:last_modified_date)";

        Map<String, Object> map = new HashMap<>();

        map.put("product_name", productDTO.getProduct_name());
        map.put("category", productDTO.getCategory().toString());
        map.put("image_url", productDTO.getImage_url());
        map.put("price", productDTO.getPrice());
        map.put("stock", productDTO.getStock());
        map.put("description", productDTO.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public void updateProduct(Integer productId, ProductDTO productDTO) {
        String sql = "UPDATE product SET product_name = :product_name,category = :category, image_url = :image_url," +
                "price = :price,stock = :stock,description = :description,last_modified_date = :last_modified_date " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("product_name", productDTO.getProduct_name());
        map.put("category", productDTO.getCategory().toString());
        map.put("image_url", productDTO.getImage_url());
        map.put("price", productDTO.getPrice());
        map.put("stock", productDTO.getStock());
        map.put("description", productDTO.getDescription());

        Date now = new Date();
        map.put("last_modified_date", now);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productid";

        Map<String,Object> map = new HashMap<>();

        map.put("productid",productId);

        namedParameterJdbcTemplate.update(sql,map);
    }

    private String getproductFilter(String sql,Map<String,Object> map,ProductQueryParam productQueryParam){

        if (productQueryParam.getProductCategory() != null){
            sql += " AND category = :category";
            map.put("category",productQueryParam.getProductCategory().name());
        }
        if (productQueryParam.getSearch() != null){
            sql += " AND product_name LIKE :product_name";
            map.put("product_name","%" + productQueryParam.getSearch() + "%");
        }

        return sql;
    }




}
