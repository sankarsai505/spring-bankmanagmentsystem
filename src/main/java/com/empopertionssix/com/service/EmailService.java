package com.empopertionssix.com.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ✅ Send HTML Email
    public void sendAccountReminderEmail(String customerEmail, String customerName) {

        // 🔒 Validation
        if (customerEmail == null || customerEmail.isBlank()) {
            throw new IllegalArgumentException("Customer email is required");
        }

        // 🔒 Safe name handling
        String safeName = (customerName == null || customerName.isBlank())
                ? "Customer"
                : customerName.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(customerEmail);
            helper.setFrom(fromEmail);
            helper.setSubject("🏦 Create Your Bank Account - Sankars Bank of India");

            // ✅ IMPORTANT: Not null anymore
            helper.setText(buildHtmlEmailContent(safeName), true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Failed to send email to: " + customerEmail, e);
        }
    }

    // ✅ HTML Email Template
    private String buildHtmlEmailContent(String name) {
        return """
                <!DOCTYPE html>
                <html>
                <body style="font-family: Arial, sans-serif;">

                    <h2>🏦 Sankars Bank of India</h2>

                    <p>Dear <strong>%s</strong>,</p>

                    <p>Welcome! 🎉</p>

                    <p>You have successfully created your customer profile, but you have not yet created a bank account.</p>

                    <p><b>Why create an account?</b></p>
                    <ul>
                        <li>Secure Savings Account</li>
                        <li>24/7 Digital Banking</li>
                        <li>Competitive Interest Rates</li>
                    </ul>

                    <p>
                        👉 <a href="https://bankofind.com/accounts/create">
                        Click here to create your account
                        </a>
                    </p>

                    <br>
                    <p>Regards,<br>Sankars Bank Team</p>

                </body>
                </html>
                """.formatted(name);
    }
}