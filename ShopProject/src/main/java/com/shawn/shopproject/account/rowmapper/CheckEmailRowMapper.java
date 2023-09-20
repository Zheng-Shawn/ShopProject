package com.shawn.shopproject.account.rowmapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckEmailRowMapper implements RowMapper<Integer> {


    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {

        Integer result = resultSet.getInt("booleancol");

        return result;
    }
}
