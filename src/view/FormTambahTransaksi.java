package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Date;
import java.util.ArrayList;
import javax.swing.*;
import java.util.List;

import controller.TransaksiController;
import model.CurrentUser;
import model.Customer;
import model.Transaction;

public class FormTambahTransaksi {

    private JFrame frame;

    public FormTambahTransaksi() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Customer customer = currentUser.getCustomer();
        showFormTambahTransaksi(customer);
    }

    public void showFormTambahTransaksi(Customer customer) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Tambah Transaksi");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JLabel namLabel = new JLabel("Input Nama : ");
        namLabel.setBounds(120, 90, 500, 50);
        panel.add(namLabel);

        JTextField NamaField = new JTextField(16);
        NamaField.setHorizontalAlignment(JTextField.CENTER);
        NamaField.setBounds(120, 130, 260, 50);
        NamaField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(NamaField);

        JLabel addressLabel = new JLabel("Input address : ");
        addressLabel.setBounds(120, 170, 500, 50);
        panel.add(addressLabel);

        JTextField addressField = new JTextField(16);
        addressField.setHorizontalAlignment(JTextField.CENTER);
        addressField.setBounds(120, 210, 260, 50);
        addressField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(addressField);

        JLabel noTelpLabel = new JLabel("Input Nomor Telepon : ");
        noTelpLabel.setBounds(120, 250, 500, 50);
        panel.add(noTelpLabel);

        JTextField noTelpField = new JTextField(16);
        noTelpField.setHorizontalAlignment(JTextField.CENTER);
        noTelpField.setBounds(120, 290, 260, 50);
        noTelpField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(noTelpField);

        JLabel beratLabel = new JLabel("Input Berat Paket : ");
        beratLabel.setBounds(120, 330, 500, 50);
        panel.add(beratLabel);

        JTextField beratField = new JTextField(16);
        beratField.setHorizontalAlignment(JTextField.CENTER);
        beratField.setBounds(120, 370, 260, 50);
        beratField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(beratField);

        JComboBox<String> pilihTypePackage = new JComboBox<>(
                new String[] { "REGULAR", "FAST", "SUPERFAST" });
        pilihTypePackage.setBounds(120, 450, 260, 40);
        pilihTypePackage.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(pilihTypePackage);

        pilihTypePackage.setSelectedIndex(0);

        JButton createButton = new JButton("Create");
        createButton.setBounds(120, 600, 260, 50);
        Component.styleButton(createButton, new Color(255, 69, 58), buttonFont);
        panel.add(createButton);

        createButton.addActionListener(e -> {
            String name = NamaField.getText();
            String address = addressField.getText();
            String noTelp = noTelpField.getText();
            double selectedWeight = Double.parseDouble(beratField.getText());
            String selectedTypePackage = (String) pilihTypePackage.getSelectedItem();
            Date createdAt = new Date(System.currentTimeMillis());

            if (!name.isEmpty() && !address.isEmpty() && !noTelp.isEmpty() && selectedWeight > 0
                    && !selectedTypePackage.isEmpty()) {
                int total_cost = (int) Transaction.roundBerat(selectedWeight);

                Transaction newTransaction = new Transaction(0, customer.getId(), selectedTypePackage, selectedWeight,
                        total_cost, createdAt, name, address, noTelp);

                List<Transaction> transaction = new ArrayList<>();
                transaction.add(newTransaction);

                boolean isCreated = TransaksiController.createTransaction(newTransaction, customer);

                if (isCreated) {
                    JOptionPane.showMessageDialog(frame, "Transaction berhasil dibuat");
                    frame.dispose();
                    new MenuCustomer();
                } else {
                    JOptionPane.showMessageDialog(frame, "Treansaction gagal dibuat");
                    return;
                }
            }
        });

        JButton exitButton = new JButton(
                "Back to Menu Customer");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            new MenuCustomer();
        });

        frame.add(panel);
        frame.setVisible(true);

    }
}