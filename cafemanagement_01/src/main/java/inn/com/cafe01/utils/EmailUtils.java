package inn.com.cafe01.utils;

import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailUtils {
	
	@Autowired
	private JavaMailSender  emailSender;
	
	public void sendSimpleMessage(String to,String subject,String text,List<String> list) {
		
		try {
		log.info("sendSimpleMessage   "+to);
		log.info("sendSimpleMessage   "+subject);
		log.info("sendSimpleMessage   "+text);
		log.info("sendSimpleMessage   "+list);
		
		SimpleMailMessage message=new SimpleMailMessage();
		log.info("step_01");
		message.setFrom("sathiyarajsoftware@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		if(list !=null && list.size()>0)
			log.info("for");
		message.setCc(getCcArray(list));
		log.info("step_02");
		emailSender.send(message);
		}catch(Exception ex) {
			log.info("error");
			log.info(ex.toString());
			ex.printStackTrace();
		}
	} 
	
	private String[] getCcArray(List<String> ccList) {
		String[] cc=new String[ccList.size()];
		for(int i=0;i<ccList.size();i++) {
			cc[i]=ccList.get(i);
		}
		return cc;
	}
	
	public void forgetMail(String to,String subject,String password) throws MessagingException {
		
		MimeMessage message=emailSender.createMimeMessage();
		MimeMessageHelper helper=new MimeMessageHelper(message, true);
		helper.setFrom("sathiyarajsoftware@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> " + to + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
		message.setContent(htmlMsg, "text/html");
		emailSender.send(message);
	}

}
