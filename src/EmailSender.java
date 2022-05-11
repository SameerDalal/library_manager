// code used from https://youtu.be/4dsrrMGKY6g
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class EmailSender {
    final String senderEmail = "sameerdalal747@gmail.com";
    final String senderPassword = "ddshxdwefvnidnyu";
    final String emailSMTPserver = "smtp.gmail.com";
    final String emailServerPort = "465";


    public void sendEmail(String receiverEmail, String subject, String body) {
        Properties properties = new Properties();
        properties.put("mail.smtp.user",senderEmail);
        properties.put("mail.smtp.host", emailSMTPserver);
        properties.put("mail.smtp.port", emailServerPort);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", emailServerPort);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        SecurityManager security = System.getSecurityManager();

        try {
            Authenticator auth = new SMTPAuthenticator();
            Session session = Session.getInstance(properties, auth);
            MimeMessage msg = new MimeMessage(session);
            msg.setText(body);
            msg.setSubject(subject);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            Transport.send(msg);
            System.out.println("Message sent");
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(senderEmail, senderPassword);
        }
    }
}
