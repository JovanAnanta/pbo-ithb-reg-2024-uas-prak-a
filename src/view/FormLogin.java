package view;

import javax.swing.*;
import controller.UserController;
import model.Customer;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class FormLogin {

    private JFrame frame;

    public FormLogin() {
        showLogin();
    }

    public void showLogin() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Login");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        ImageIcon originalIcon = new ImageIcon("lib/img2.jpg");
        Image img = originalIcon.getImage();
        Image resizedImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImg);
        JLabel imageLabel = new JLabel(resizedIcon);

        imageLabel.setBounds(-15, -10, 200, 200);
        frame.add(imageLabel);

        JLabel title = new JLabel("LOG-IN");
        title.setBounds(200, 150, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel noTelpLabel = new JLabel("Input Nomor Telepon : ");
        noTelpLabel.setBounds(120, 200, 500, 50);
        panel.add(noTelpLabel);

        JTextField noTelpField = new JTextField(16);
        noTelpField.setHorizontalAlignment(JTextField.CENTER);
        noTelpField.setBorder(BorderFactory.createEmptyBorder());
        noTelpField.setBounds(120, 240, 260, 50);
        panel.add(noTelpField);

        JLabel passLabel = new JLabel("Input Password : ");
        passLabel.setBounds(120, 280, 500, 50);
        panel.add(passLabel);

        JPasswordField inputPassword = new JPasswordField(16);
        inputPassword.setHorizontalAlignment(JTextField.CENTER);
        inputPassword.setBorder(BorderFactory.createEmptyBorder());
        inputPassword.setBounds(120, 320, 260, 50);
        panel.add(inputPassword);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBounds(120, 400, 260, 50);
        Component.styleButton(loginButton, new Color(3, 123, 252), buttonFont);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String noTelp = noTelpField.getText();
            String password = new String(inputPassword.getPassword());

            if (!noTelp.isEmpty() && !password.isEmpty()) {
                Customer verifying = UserController.verifyUser(noTelp, password);
                if (verifying == null) {
                    JOptionPane.showMessageDialog(frame, "Salah nomor telepon/password!");
                } else {
                    JOptionPane.showMessageDialog(frame, "LogIn berhasil");
                    frame.dispose();
                    new MenuCustomer();
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu YOK!");
            }
        });

        JButton exitButton = new JButton("Back to Main Menu");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MainMenu();
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
