package com.shawn.shopproject.account.service.impl;


import com.shawn.shopproject.account.dao.MemberDao;
import com.shawn.shopproject.account.dto.MemberLoginRequest;
import com.shawn.shopproject.account.dto.MemberQueryParam;
import com.shawn.shopproject.account.dto.MemberRegisterRequest;
import com.shawn.shopproject.account.entity.Member;
import com.shawn.shopproject.account.service.MemberService;
import com.shawn.shopproject.util.mailservice.RandonCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MemberServiceimpl implements MemberService {

    // 資料與資料庫驗證 若重複便紀錄log,發出400給前端
    private final static Logger log = LoggerFactory.getLogger(MemberServiceimpl.class);

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private RandonCode randonCode;


    @Override
    public Integer register(MemberRegisterRequest memberRegisterRequest) {

        Member member = memberDao.getMemberByEmail(memberRegisterRequest.getEmail());

        // 檢查帳號是否存在
        if (member != null) {
            log.warn("該email:{}已被註冊", memberRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 檢查驗證碼是否正確
        String randoncode = randonCode.getRandonCode(memberRegisterRequest.getEmail());
        if (!randoncode.equals(memberRegisterRequest.getValidcode())) {
            log.warn("驗證碼錯誤");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5(Hash函式的一種實現方式) 生成雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(memberRegisterRequest.getPassword().getBytes());
        memberRegisterRequest.setPassword(hashedPassword);

        return memberDao.createdMember(memberRegisterRequest);
    }

    @Override
    public Member getMemberById(Integer memberid) {
        return memberDao.getMemberById(memberid);
    }

    @Override
    public Member login(MemberLoginRequest memberLoginRequest) {
        Member member = memberDao.getMemberByEmail(memberLoginRequest.getEmail());

        // 檢查帳號是否存在
        if (member == null) {
            log.warn("該email:{} 尚未註冊", memberLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 使用MD5 生成雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(memberLoginRequest.getPassword().getBytes());

        // 檢查密碼是否正確
        if (member.getPassword().equals(hashedPassword)) {
            return member;
        } else {
            log.warn("該email:{} 嘗試登入,密碼錯誤", memberLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Boolean checkMail(String email) {
        return memberDao.checkMail(email);
    }

    @Override
    public void updatePassword(MemberLoginRequest memberLoginRequest) {

        //檢查網域是否與提供驗證碼一致避免外部隨意進入
        String randoncode = randonCode.getRandonCode(memberLoginRequest.getEmail());
        if (randoncode == null || !randoncode.equals(memberLoginRequest.getRandoncode())) {
            log.warn("更新密碼時網域比對驗證錯誤");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 使用MD5 生成雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(memberLoginRequest.getPassword().getBytes());

        memberDao.updatePassword(memberLoginRequest.getEmail(), hashedPassword);
    }
}
