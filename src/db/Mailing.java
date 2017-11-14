package db;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

class Mailing {

    private static String from;
    private static String pass;

    private void getDbInfo(){


        try{
            Properties pro = new Properties();
            InputStream input = null;
            input = new FileInputStream("resources/config.properties");
            pro.load(input);
            StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
            decryptor.setPassword("mySecretPassword");
            from =decryptor.decrypt(pro.getProperty("DB_EMAIL"));
            pass =decryptor.decrypt(pro.getProperty("DB_EPWD"));


        }catch(Exception e){
            e.printStackTrace();
        }
    }


    void sendmail(String RECIPIENT, String subject, String body) {
        getDbInfo();
        String[] to = { RECIPIENT };
        Properties props = System.getProperties();
        String host = "smtp.pvpharm.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for (InternetAddress toAddres : toAddress) {
                message.addRecipient(Message.RecipientType.TO, toAddres);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            System.out.println("mail sent");
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}