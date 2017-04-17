package util;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailManager {
	final static String SSL = "true";
	final static String TLS = "true";
	final static String AUTH = "true";
	final static String DEBUG = "true";
	final static String FALLBACK = "false";
	final static String PORT = "465";
	final static String HOST = "smtp.gmail.com";
	final static String NAME = "(주)아소코리아";
	final static String ID = "astkdev";
	final static String MAIL = "gmail.com";
	final static String PW = "k2341455!";
	
	public static boolean sendCheckEmail(String OTC, String target) {
		final String TITLE = "(주)아소코리아 가입 인증 메일입니다.";
		final String CONTENT = "메일을 인증하려면 아래의 링크를 눌러주세요. <br>링크 : http://14.45.107.227:8080/AD/EmailCheck.ad?OTC="+OTC+" ";
		return sendMail(TITLE, CONTENT, target);
	}
	
	public static boolean sendTest() {
		final String TITLE = "테스트 메일";
		final String CONTENT = "테스트 내용<br>하이 <a href='#'>짠</a>";
		final String TARGET = "rkdwocks91@naver.com";
		return sendMail(TITLE, CONTENT, TARGET);
	}
	
	private static boolean sendMail(String title, String content, String target) {
		Properties props = new Properties();
		props.put("mail.smtp.user", ID );
		props.put("mail.smtp.host", HOST );
		props.put("mail.smtp.port", PORT );
		props.put("mail.smtp.starttls.enable", TLS );
		//props.put("mail.smtp.ssl.enable", SSL );
		props.put("mail.smtp.auth", AUTH );
		props.put("mail.smtp.debug", DEBUG );
		
		props.put("mail.smtp.socketFactory.port", PORT );
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", FALLBACK );
		
		try {
			Authenticator auth = new SMTPAuthenticator();
			Session ses = Session.getInstance(props, auth);
			MimeMessage msg = new MimeMessage(ses);

			Address fromAddr = new InternetAddress(ID+ "@"+ MAIL);
			Address toAddr = new InternetAddress(target);
			
			msg.setSubject(title);
			msg.setFrom(fromAddr);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html; charset=UTF-8");
			
			Transport.send(msg);
			return true;
			
		} catch (Exception ex) {
			System.out.println("log : try-catch.."+ ASTKLogManager.getMethodName_withClassName() +"\n"+ ex);
			return false; 
			
		} finally {
			
		}
	}
	
	private static class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(ID, PW);
		}
	}
}
