package com.shawn.shopproject.account.dao.impl;

import com.shawn.shopproject.account.dao.MemberDao;
import com.shawn.shopproject.account.dto.MemberRegisterRequest;
import com.shawn.shopproject.account.entity.Member;
import com.shawn.shopproject.account.rowmapper.CheckEmailRowMapper;
import com.shawn.shopproject.account.rowmapper.MemberRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDaoimpl implements MemberDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public Integer createdMember(MemberRegisterRequest memberRegisterRequest) {
        String sql = "INSERT INTO user(email,password,createdDate,lastModifiedDate,name,tel) VALUES(:email,:password,:createdDate,:lastModifiedDate,:name,:tel)";

        Map<String, Object> map = new HashMap<>();

        Date date = new Date();
        map.put("email", memberRegisterRequest.getEmail());
        map.put("password", memberRegisterRequest.getPassword());
        map.put("createdDate", date);
        map.put("lastModifiedDate", date);
        map.put("name", memberRegisterRequest.getName());
        map.put("tel", memberRegisterRequest.getTel());

        //取得生成的PK值
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int memberid = keyHolder.getKey().intValue();

        return memberid;
    }

    @Override
    public Member getMemberById(Integer memberid) {
        String sql = "SELECT userId,email,password,createdDate,lastModifiedDate,name,tel FROM user WHERE userId = :userId";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", memberid);

        List<Member> memberList = namedParameterJdbcTemplate.query(sql, map, new MemberRowMapper());

        if (memberList.size() > 0) {
            return memberList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Member getMemberByEmail(String email) {
        String sql = "SELECT userId,email,password,createdDate,lastModifiedDate,name,tel FROM user WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<Member> memberList = namedParameterJdbcTemplate.query(sql, map, new MemberRowMapper());
        if (memberList.size() > 0) {
            return memberList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public boolean checkMail(String email) {
        String sql = "SELECT COUNT(*) > 0 AS booleancol FROM user WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<Integer> list = namedParameterJdbcTemplate.query(sql, map, new CheckEmailRowMapper());
        if (list.get(0) == 1) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updatePassword(String email, String password) {
        String sql = "UPDATE user SET password = :password WHERE email = :email";

        Map<String,Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
