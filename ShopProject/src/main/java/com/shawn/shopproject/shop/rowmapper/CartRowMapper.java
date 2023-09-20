package com.shawn.shopproject.shop.rowmapper;

import com.shawn.shopproject.shop.dto.CartList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CartRowMapper implements RowMapper<CartList> {


    @Override
    public CartList mapRow(ResultSet resultSet, int i) throws SQLException {

        CartList cartList = new CartList();

        cartList.setProductid(resultSet.getInt("productid"));
        cartList.setQuantity(resultSet.getInt("quantity"));

        return cartList;
    }
}
