package com.shawn.shopproject.util.mailservice;


import com.github.dozermapper.core.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;

@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigProperties configProperties; //讀取配置屬性

    @Autowired
    private JavaMailSender javaMailSender; // 發送器

    @Autowired
    private Mapper mapper; // 對象映射轉換器


    // 發送簡單的文本信件 若沒設定信箱，會取得設定檔的信箱帳號發送
    public void sendSimpleMailMessage(MailDTO mailDTO) {
        SimpleMailMessage simpleMailMessage = mapper.map(mailDTO, SimpleMailMessage.class);
        if (StringUtils.isEmpty(mailDTO.getFrom())) {
            mailDTO.setFrom(configProperties.getFrom());
        }
        javaMailSender.send(simpleMailMessage);
    }

    // 發送複雜包含附件及html內容的信件
    public void sendMimeMessage(MailDTO mailDTO) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true);

            if (StringUtils.isEmpty(mailDTO.getFrom())) {
                messageHelper.setFrom(configProperties.getFrom());
            } else {
                messageHelper.setFrom(mailDTO.getFrom());
            }
            messageHelper.setTo(mailDTO.getTo());
            messageHelper.setSubject(mailDTO.getSubject());

            mimeMessage = messageHelper.getMimeMessage();
            // 將附件創建對象 處理html內容
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mailDTO.getText(), "text/html;charset=UTF-8");

            // 創建對象 描述數據關係 將附件添加到 mm 中
            MimeMultipart mm = new MimeMultipart();
            mm.setSubType("related");
            mm.addBodyPart(mimeBodyPart);
            if (mailDTO.getFilenames() != null && mailDTO.getFilenames().length > 0) {
                for (String filename : mailDTO.getFilenames()) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    try {
                        attachPart.attachFile(filename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mm.addBodyPart(attachPart);
                }
            }
            mimeMessage.setContent(mm);
            mimeMessage.saveChanges();

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mimeMessage);
    }


}
