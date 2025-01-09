package view;

import javax.swing.*;

import controller.UserController;
import java.awt.geom.RoundRectangle2D;
import java.awt.*;

public class FormRegister {

    private JFrame frame;

    public FormRegister() {
        showRegister();
    }

    public void showRegister() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Register");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel title = new JLabel("REGISTER");
        title.setBounds(190, 75, 500, 50);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        panel.add(title);

        JLabel noTelpLabel = new JLabel("Input Nomor Telepon : ");
        noTelpLabel.setBounds(120, 150, 500, 50);
        panel.add(noTelpLabel);

        JTextField noTelpField = new JTextField(16);
        noTelpField.setHorizontalAlignment(JTextField.CENTER);
        noTelpField.setBounds(120, 190, 260, 50);
        noTelpField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(noTelpField);

        JLabel namLabel = new JLabel("Input Nama : ");
        namLabel.setBounds(120, 230, 500, 50);
        panel.add(namLabel);

        JTextField NamaField = new JTextField(16);
        NamaField.setHorizontalAlignment(JTextField.CENTER);
        NamaField.setBounds(120, 270, 260, 50);
        NamaField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(NamaField);

        JLabel addressLabel = new JLabel("Input address : ");
        addressLabel.setBounds(120, 310, 500, 50);
        panel.add(addressLabel);

        JTextField addressField = new JTextField(16);
        addressField.setHorizontalAlignment(JTextField.CENTER);
        addressField.setBounds(120, 350, 260, 50);
        addressField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(addressField);

        JLabel passLabel = new JLabel("Input Password : ");
        passLabel.setBounds(120, 390, 500, 50);
        panel.add(passLabel);

        JPasswordField inputPassword = new JPasswordField(16);
        inputPassword.setHorizontalAlignment(JTextField.CENTER);
        inputPassword.setBounds(120, 430, 260, 50);
        inputPassword.setBorder(BorderFactory.createEmptyBorder());
        panel.add(inputPassword);

        JButton regisButton = new JButton("REGISTER");
        regisButton.setBounds(120, 530, 260, 50);
        Component.styleButton(regisButton, new Color(3, 123, 252), buttonFont);
        panel.add(regisButton);

        regisButton.addActionListener(e -> {
            String noTelp = noTelpField.getText();
            String name = NamaField.getText();
            String address = addressField.getText();
            String password = new String(inputPassword.getPassword());

            if (!name.isEmpty() && !password.isEmpty() && !address.isEmpty() && !noTelp.isEmpty()) {
                boolean verify = UserController.verifyRegister(password, name, address, noTelp);
                if (verify) {
                    frame.dispose();
                    new MainMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Name/email sudah terdaftar!");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Isi terlebih dahulu yak!");
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