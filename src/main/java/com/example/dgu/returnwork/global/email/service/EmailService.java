package com.example.dgu.returnwork.global.email.service;

import com.example.dgu.returnwork.global.exception.BaseException;
import com.example.dgu.returnwork.global.exception.CommonErrorCode;
import com.example.dgu.returnwork.global.properties.EmailProperties;
import com.example.dgu.returnwork.global.util.RedisUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final MailContentBuilder mailContentBuilder;
    private final RedisUtil redisUtil;
    private final JavaMailSender javaMailSender;
    private final EmailProperties emailProperties;

    public void sendEmailAuthentication(String toEmail){
        String certifyNum = redisUtil.createCertifyNum();
        String message = mailContentBuilder.build(certifyNum);

        sendEmail(toEmail, message);

        redisUtil.createRedisData(toEmail, certifyNum);
        log.info("인증 이메일 발송 완료 {}", toEmail);
    }

    private void sendEmail(String toEmail, String content) {
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("ReturnWork 이메일 인증");
            helper.setText(content, true);
            helper.setFrom("ReturnWork <" + emailProperties.getUsername() + ">");

            javaMailSender.send(mimeMessage);
        }catch (MessagingException e ){
            log.error("이메일 발송 실패: {} - {}", toEmail, e.getMessage());
            throw BaseException.type(CommonErrorCode.EMAIL_SEND_FAILED);
        }
    }
}
