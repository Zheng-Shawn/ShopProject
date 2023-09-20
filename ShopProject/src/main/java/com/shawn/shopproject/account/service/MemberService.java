package com.shawn.shopproject.account.service;

import com.shawn.shopproject.account.dto.MemberLoginRequest;
import com.shawn.shopproject.account.dto.MemberQueryParam;
import com.shawn.shopproject.account.dto.MemberRegisterRequest;
import com.shawn.shopproject.account.entity.Member;

public interface MemberService {


    Integer register(MemberRegisterRequest memberRegisterRequest);
    Member getMemberById(Integer memberid);
    Member login(MemberLoginRequest memberLoginRequest);
    Boolean checkMail(String email);
    void updatePassword(MemberLoginRequest memberLoginRequest);

}
