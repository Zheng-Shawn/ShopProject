package com.shawn.shopproject.account.rowmapper;

import com.shawn.shopproject.account.entity.Member;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberRowMapper implements RowMapper<Member> {


    @Override
    public Member mapRow(ResultSet resultSet, int i) throws SQLException {
        Member member = new Member();

        member.setUserId(resultSet.getInt("userId"));
        member.setEmail(resultSet.getString("email"));
        member.setPassword(resultSet.getString("password"));
        member.setCreatedDate(resultSet.getTimestamp("createdDate"));
        member.setLastModifiedDate(resultSet.getTimestamp("lastModifiedDate"));
        member.setName(resultSet.getString("name"));
        member.setTel(resultSet.getString("tel"));

        return member;
    }
}
