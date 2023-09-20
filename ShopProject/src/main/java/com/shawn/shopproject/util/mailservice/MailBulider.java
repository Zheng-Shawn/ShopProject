package com.shawn.shopproject.util.mailservice;


import com.shawn.shopproject.account.dto.MemberQueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MailBulider {

    @Autowired
    private MailService mailService;

    @Autowired
    private RandonCode randonCode;

    public void sendSimpleEmail(MemberQueryParam memberQueryParam) {

        MailDTO mailDTO = new MailDTO();

        // 產生驗證碼 存入Redis
        String randoncode = randonCode.RandonCode();
        randonCode.saveRandonCode(memberQueryParam.getEmail(), randoncode);

        // 設定信件內容
        mailDTO.setTo(new String[]{memberQueryParam.getEmail()});
        mailDTO.setSubject("來自Shawn發出的註冊驗證碼");
        mailDTO.setText("這是註冊驗證碼請於兩分鐘內輸入:" + randoncode);

        // 最後傳給MailService寄信
        mailService.sendSimpleMailMessage(mailDTO);

    }

    public void sendForgetMail(MemberQueryParam memberQueryParam) {

        MailDTO mailDTO = new MailDTO();
        String email = memberQueryParam.getEmail();

        // 產生驗證碼 存入Redis
        String randoncode = randonCode.RandonCode();
        randonCode.saveRandonCode(memberQueryParam.getEmail(), randoncode);

        mailDTO.setTo(new String[]{memberQueryParam.getEmail()});
        mailDTO.setSubject("來自Shawn發出的密碼重設信件");
        mailDTO.setText("<html><body><h2>您好!請重設定密碼</h2><a href='http://localhost:8080/Shawn/util/updatepassword.html?randoncode=" + randoncode + "&email=" + email + "'>Click me</a><p>請於兩分鐘內修改完畢</p></body></html>");
        mailDTO.setFrom("z.shawn.contact@gmail.com");

        mailService.sendMimeMessage(mailDTO);

    }


}
