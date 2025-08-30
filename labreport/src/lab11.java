import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailSenderGUI extends JFrame {
    // GUI Components
    private JTextField senderField, receiverField, subjectField;
    private JTextArea messageArea;
    private JButton sendButton;

    public EmailSenderGUI() {
        setTitle("Email Sender");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for text fields
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Sender Email:"));
        senderField = new JTextField();
        panel.add(senderField);

        panel.add(new JLabel("Receiver Email:"));
        receiverField = new JTextField();
        panel.add(receiverField);

        panel.add(new JLabel("Subject:"));
        subjectField = new JTextField();
        panel.add(subjectField);

        panel.add(new JLabel("Message:"));
        messageArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane);

        add(panel, BorderLayout.CENTER);

        // Send button
        sendButton = new JButton("Send Email");
        add(sendButton, BorderLayout.SOUTH);

        // Action listener for button
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        String sender = senderField.getText();
        String receiver = receiverField.getText();
        String subject = subjectField.getText();
        String message = messageArea.getText();

        // SMTP configuration (example for Gmail)
        String host = "smtp.gmail.com"; // SMTP server
        String username = sender;       // Sender email
        String password = "yourpassword"; // Sender email password or app password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new jakarta.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            msg.setSubject(subject);
            msg.setText(message);

            Transport.send(msg);

            JOptionPane.showMessageDialog(this, "Email Sent Successfully!");
        } catch (MessagingException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to send email: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmailSenderGUI gui = new EmailSenderGUI();
            gui.setVisible(true);
        });
    }
}

