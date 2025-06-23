package Utils;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class EmailSender {

    private static final String BASE_URL = "http://localhost:8080/GiaSuTot_war";
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

    public static void sendReceiptEmail(String toEmail, String studentId, String courseId, String fileName, String filePath) throws MessagingException, UnsupportedEncodingException {
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

        // Tạo message multipart
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Biên lai thanh toán khóa học " + courseId);

        // Tạo nội dung HTML với ảnh nhúng
        String htmlContent = "<h2>Xin chào,</h2>"
                + "<p>Đây là biên lai thanh toán từ học sinh <strong>" + studentId + "</strong> cho khóa học <strong>" + courseId + "</strong>.</p>"
                + "<p>Thông tin chi tiết:</p>"
                + "<ul>"
                + "<li>Tên file: <strong>" + fileName + "</strong></li>"
                + "<li>Đường dẫn: <strong>" + filePath + "</strong></li>"
                + "</ul>"
                + "<p>Hình ảnh biên lai:</p>"
                + "<img src=\"cid:image\" alt=\"Biên lai\" style=\"max-width: 100%; height: auto;\">"
                + "<p>Vui lòng kiểm tra và xác nhận với admin nếu cần.</p>"
                + "<p>Trân trọng,<br/>Đội ngũ Gia Sư Tốt</p>";

        // Tạo multipart message
        Multipart multipart = new MimeMultipart();

        // Phần HTML
        BodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
        multipart.addBodyPart(htmlPart);

        // Phần đính kèm ảnh
        File file = new File(filePath);
        if (file.exists()) {
            MimeBodyPart imagePart = new MimeBodyPart();
            // Sử dụng DataSource để đính kèm file
            DataSource source = new FileDataSource(filePath);
            imagePart.setDataHandler(new DataHandler(source));
            imagePart.setContentID("<image>");
            imagePart.setDisposition(MimeBodyPart.INLINE); // Nhúng ảnh trực tiếp
            multipart.addBodyPart(imagePart);
        } else {
            System.err.println("File không tồn tại: " + filePath);
            htmlContent += "<p><strong>Lưu ý: Không thể nhúng ảnh vì file không tồn tại.</strong></p>";
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
        }

        message.setContent(multipart);

        Transport.send(message);
        System.out.println("✅ Đã gửi email biên lai tới: " + toEmail);
    }

    // Gửi email dạng văn bản
    public static void sendTextEmail(String toEmail, String subject, String body) throws MessagingException, UnsupportedEncodingException {
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
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("✅ Đã gửi email tới: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Gửi email thất bại tới: " + toEmail);
            e.printStackTrace();
            throw e;
        }
    }

    public static void sendTutorApprovedEmail(String toEmail, String userName) {
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
            message.setSubject("Bạn đã trở thành Gia sư - Gia Sư Tốt");

            String loginUrl = BASE_URL + "/account?action=login";
            String htmlContent = "<div style=\"font-family: Arial, sans-serif; line-height: 1.6; color: #333; max-width: 600px; margin: auto;\">" +
                    "<h2 style=\"color: #28a745;\">Chúc mừng " + userName + "!</h2>" +
                    "<p>Bạn đã được phê duyệt trở thành gia sư trên hệ thống <strong>Gia Sư Tốt</strong>.</p>" +
                    "<p>Bây giờ bạn có thể đăng nhập và bắt đầu nhận lớp phù hợp với chuyên môn của mình.</p>" +
                    "<a href=\"" + loginUrl + "\" style=\"display: inline-block; padding: 12px 24px; background-color: #007bff; color: #fff; text-decoration: none; border-radius: 4px; font-weight: bold;\">Đăng nhập ngay</a>" +
                    "<p style=\"margin-top: 20px; font-size: 14px;\">Nếu bạn có bất kỳ câu hỏi nào, hãy liên hệ với chúng tôi qua email hoặc hotline hỗ trợ.</p>" +
                    "<p style=\"font-style: italic;\">Trân trọng,<br/>Đội ngũ Gia Sư Tốt</p>" +
                    "</div>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("✅ Đã gửi email xác nhận gia sư tới: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Gửi email xác nhận gia sư thất bại tới: " + toEmail);
            e.printStackTrace();
        }
    }

}