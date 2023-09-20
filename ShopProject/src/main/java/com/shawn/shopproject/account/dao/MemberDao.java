package com.shawn.shopproject.account.dao;

import com.shawn.shopproject.account.dto.MemberRegisterRequest;
import com.shawn.shopproject.account.entity.Member;

public interface MemberDao {


    Integer createdMember(MemberRegisterRequest memberRegisterRequest);
    Member getMemberById(Integer memberid);
    Member getMemberByEmail(String email);
    boolean checkMail(String email);
    void updatePassword(String email,String password);
}
