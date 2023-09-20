package com.shawn.shopproject.shop.dao.impl;

import com.shawn.shopproject.account.entity.Member;
import com.shawn.shopproject.shop.dao.ProductDao;
import com.shawn.shopproject.shop.dto.ProductDTO;
import com.shawn.shopproject.shop.dto.ProductQueryParam;
import com.shawn.shopproject.shop.dto.CartList;
import com.shawn.shopproject.shop.entity.ProductVO;
import com.shawn.shopproject.shop.rowmapper.CartRowMapper;
import com.shawn.shopproject.shop.rowmapper.CategoryRowMapper;
import com.shawn.shopproject.shop.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductDaoimpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public ProductVO getProductById(Integer productId) {
        String sql = "SELECT productId,productName,category,price,sold,description,createdDate,lastModifiedDate,brand " +
                "FROM product " +
                "WHERE productId = :productId";

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
        //查全部或帶參數的查詢  因1=1對查詢結果不影響，但為了拼接sql變數，而要使用AND連接
        String sql = "SELECT * FROM product WHERE 1=1 ";

        Map<String, Object> map = new HashMap<>();

        //查詢條件
        sql = getproductFilter(sql, map, productQueryParam);

        //排序條件
        sql += " ORDER BY " + productQueryParam.getOrderBy() + " " + productQueryParam.getSort();

        //分頁功能 限制查詢筆數
        sql += " LIMIT :limit OFFSET :offset";
        map.put("limit", productQueryParam.getLimit());
        map.put("offset", productQueryParam.getOffset());

        List<ProductVO> list = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return list;
    }

    @Override
    public Integer getProductsTotal(ProductQueryParam productQueryParam) {
        String sql = "SELECT count(*) FROM product WHERE 1=1";

        HashMap<String, Object> map = new HashMap<>();

        sql = getproductFilter(sql, map, productQueryParam);

        //當要取得非實體的值,寫法會稍微不同於query()
        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);

        return total;
    }

    @Override
    public void addProduct(ProductDTO productDTO) {
        String sql = "INSERT INTO product(productName,category,price,sold,description,createdDate,lastModifiedDate)" +
                "VALUES (:product_name,:category,:price,:sold,:description,:created_date,:last_modified_date)";

        Map<String, Object> map = new HashMap<>();

        map.put("product_name", productDTO.getProduct_name());
        map.put("category", productDTO.getCategory().toString());
        map.put("price", productDTO.getPrice());
        map.put("sold", productDTO.getSold());
        map.put("description", productDTO.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void updateProduct(Integer productId, ProductDTO productDTO) {
        String sql = "UPDATE product SET product_name = :product_name,category = :category," +
                "price = :price,sold = :sold,description = :description,last_modified_date = :last_modified_date " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        map.put("product_name", productDTO.getProduct_name());
        map.put("category", productDTO.getCategory().toString());
        map.put("price", productDTO.getPrice());
        map.put("sold", productDTO.getSold());
        map.put("description", productDTO.getDescription());

        Date now = new Date();
        map.put("last_modified_date", now);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void deleteProductById(Integer productId) {
        String sql = "DELETE FROM product WHERE product_id = :productid";

        Map<String, Object> map = new HashMap<>();

        map.put("productid", productId);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public List<ProductVO> getAllcategory(ProductQueryParam productQueryParam) {

        String sql = "SELECT category FROM product WHERE 1=1";

        Map<String, Object> map = new HashMap<>();
        if (productQueryParam.getProductCategory() != null) {
            sql += " AND category = :category";
            map.put("category", productQueryParam.getProductCategory().name());
        }
        if (productQueryParam.getSearch() != null) {
            sql += " AND productName LIKE :productName";
            map.put("productName", "%" + productQueryParam.getSearch() + "%");
        }
        List<ProductVO> categoryList = namedParameterJdbcTemplate.query(sql, map, new CategoryRowMapper());

        return categoryList;
    }

    private String getproductFilter(String sql, Map<String, Object> map, ProductQueryParam productQueryParam) {

        if (productQueryParam.getProductCategory() != null) {
            sql += " AND category = :category";
            map.put("category", productQueryParam.getProductCategory().name());
        }
        if (productQueryParam.getSearch() != null) {
            sql += " AND productName LIKE :productName";
            map.put("productName", "%" + productQueryParam.getSearch() + "%");
        }
        if (productQueryParam.getMinprice() != null && productQueryParam.getMaxprice() != null) {
            sql += " AND price BETWEEN :minprice AND :maxprice";
            map.put("minprice", productQueryParam.getMinprice());
            map.put("maxprice", productQueryParam.getMaxprice());
        }

        return sql;
    }

    @Override
    public List<CartList> getProductsByMemberId(Integer memberid) {
        String sql = "SELECT productid,quantity FROM cart WHERE memberid = :memberid";

        Map<String, Object> map = new HashMap<>();
        map.put("memberid", memberid);

        List<CartList> list = namedParameterJdbcTemplate.query(sql, map, new CartRowMapper());

        if (list.size() > 0) {
            return list;
        } else {
            return null;
        }
    }

    @Override
    public void storeCart(Member member, List<Integer> cartlist) {
        String sql = "INSERT INTO cart (memberid, productid, quantity)" +
                "VALUES (:memberid, :productid,:quantity)" +
                "ON DUPLICATE KEY UPDATE quantity = VALUES(quantity)";
        String sql2 = "DELETE FROM cart WHERE memberid = :memberid";


        Map<String, Object> map = new HashMap<>();
        map.put("memberid", member.getUserId());

        // 先清空原有購物車資料
        namedParameterJdbcTemplate.update(sql2,map);

        if (cartlist != null && member != null) {
            // 使用Stream和Collectors來回傳list每个值及數量
            Map<Integer, Integer> counts = cartlist.stream()
                    .collect(Collectors.groupingBy(e -> e, Collectors.collectingAndThen(
                            Collectors.counting(),
                            Long::intValue // 將counting()回傳的long轉換為Integer
                    )));

            // 迭代取得每个值及數量
            counts.forEach((productid, quantity) -> {
                map.put("productid", productid);
                map.put("quantity", quantity);
                namedParameterJdbcTemplate.update(sql, map);
            });
        }
    }

    @Override
    public List<ProductVO> getProductsByCart(List<Integer> cartlist) {
        String sql = "SELECT * FROM product WHERE";

        // 使用Set去除list重複值
        Set<Integer> uniqueNumbers = new HashSet<>(cartlist);
        Integer[] array = uniqueNumbers.toArray(new Integer[uniqueNumbers.size()]);

        sql += " productId = " + array[0];
        for (int i = 1; i < array.length; i++) {
            sql += " OR productId = " + array[i];
        }

        List<ProductVO> productlist = namedParameterJdbcTemplate.query(sql, new HashMap<>(), new ProductRowMapper());
        for (ProductVO product : productlist) {
        }

        return productlist;
    }
}
