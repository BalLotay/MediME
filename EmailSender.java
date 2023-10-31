import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private static final String EMAIL = "your_email@gmail.com";
    private static final String PASSWORD = "your_email_password";

    public static void sendRejectionEmail(String recipientEmail) {
        String subject = "Application Rejection";
        String messageText = "We regret to inform you that your application has been rejected.";

        sendEmail(recipientEmail, subject, messageText);
    }

    public static void sendAcceptanceEmail(String recipientEmail) {
        String subject = "Application Acceptance";
        String messageText = "Congratulations! Your application has been accepted.";

        sendEmail(recipientEmail, subject, messageText);
    }

    private static void sendEmail(String recipientEmail, String subject, String messageText) {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject(subject);
            message.setText(messageText);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
