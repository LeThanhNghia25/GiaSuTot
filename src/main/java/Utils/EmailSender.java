package Utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.util.Properties;

public class EmailSender {

    private static final String FROM_EMAIL = "hanh2803riri@gmail.com";
    private static final String FROM_NAME = "GiaSuTot";
    private static final String APP_PASSWORD = "tdjb mgck fqvt hsse"; // Thay bằng App Password Gmail

    public static void sendActivationEmail(String toEmail, String userName, String activationLink) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Xác nhận đăng nhập - Gia Sư Tốt");

            String htmlContent = "<h2>Xin chào " + userName + ",</h2>"
                    + "<p>Vui lòng nhấn nút bên dưới để xác nhận đăng nhập và kích hoạt tài khoản của bạn.</p>"
                    + "<a href=\"" + activationLink + "\" "
                    + "style=\"display: inline-block; padding: 12px 24px; background-color: #28a745; color: white; text-decoration: none; border-radius: 6px; font-weight: bold;\">"
                    + "Xác nhận đăng nhập"
                    + "</a>"
                    + "<p style=\"margin-top: 20px;\">Nếu bạn không thực hiện hành động này, vui lòng bỏ qua email này.</p>"
                    + "<p>Trân trọng,<br/>Đội ngũ Gia Sư Tốt</p>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("✅ Đã gửi email kích hoạt tới: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Gửi email kích hoạt thất bại tới: " + toEmail);
            e.printStackTrace();
        }
    }

    public static void sendVerificationCodeEmail(String toEmail, String code) {
        // Cấu hình SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, APP_PASSWORD);
            }
        });

        try {
            // Soạn nội dung email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Mã xác thực đặt lại mật khẩu - Gia Sư Tốt");

            String content = "Xin chào,\n\n"
                    + "Bạn đã yêu cầu đặt lại mật khẩu cho tài khoản tại Gia Sư Tốt.\n"
                    + "Mã xác thực của bạn là: " + code + "\n\n"
                    + "Vui lòng nhập mã này vào trang xác thực để tiếp tục.\n"
                    + "Nếu bạn không yêu cầu điều này, vui lòng bỏ qua email này.\n\n"
                    + "Trân trọng,\nĐội ngũ Gia Sư Tốt";

            message.setText(content);

            Transport.send(message);
            System.out.println("✅ Đã gửi mã xác thực đến: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Gửi email thất bại tới: " + toEmail);
            e.printStackTrace();
        }
    }

}
