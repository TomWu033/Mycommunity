package tom.community.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSender implements InitializingBean {

    private JavaMailSenderImpl mailSender;
    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender=new JavaMailSenderImpl();
        mailSender.setUsername("");
        mailSender.setPassword("");
        mailSender.setHost("");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf8");
        Properties javaMailProperties=new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable",true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
    public boolean sendWithHTMLTemplate(String to, String subject, String template, Map<String,Object> model){
        try {
            String nick= MimeUtility.encodeText("Tom社区");
            InternetAddress form=new InternetAddress(nick+"<>");
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
            String result="";//模板工具
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom(form);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(result,true);
            mailSender.send(mimeMessage);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
