package db;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Testing {


public void main (String args []){
    final String username = "";
    final String password = "";
    Properties props = new Properties();
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");

    Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(""));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(""));
			message.setSubject("PVpharm License Key");
			message.setText("Dear customer,"
				+ "\n\n Thank you for purchase our product, you License key is XXX-XXX-XXX."
				+ "The license wil expire on 01-11-2017 "
				+ "for anyother enquires, please visit www.pvpharm.com. Email yun@PVpharm.com"
				+ "Thank you."
				+ "\n\n\n"
				+ "\n Yun \n Lead developer in PVpharm Technology Team");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {

            System.out.println("nope");
		}

	}
}