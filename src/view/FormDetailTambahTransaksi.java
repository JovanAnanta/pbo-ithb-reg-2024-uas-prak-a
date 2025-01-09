package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Date;
import javax.swing.*;

import controller.TransaksiController;
import model.CurrentUser;
import model.Customer;
import model.ShipmentDetail;
import model.Status;

public class FormDetailTambahTransaksi {

    private JFrame frame;
    private String filePath = null; // Menyimpan path file gambar

    public FormDetailTambahTransaksi() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Customer customer = currentUser.getCustomer();
        showFormDetailTambahTransaksi(customer);
    }

    public void showFormDetailTambahTransaksi(Customer customer) {
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

        JLabel transIdLabel = new JLabel("Transaction ID : ");
        transIdLabel.setBounds(120, 90, 500, 50);
        panel.add(transIdLabel);

        JTextField transIDField = new JTextField(16);
        transIDField.setHorizontalAlignment(JTextField.CENTER);
        transIDField.setBounds(120, 130, 260, 50);
        transIDField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(transIDField);

        JLabel positionLabel = new JLabel("Current position : ");
        positionLabel.setBounds(120, 170, 500, 50);
        panel.add(positionLabel);

        JTextField positionField = new JTextField(16);
        positionField.setHorizontalAlignment(JTextField.CENTER);
        positionField.setBounds(120, 210, 260, 50);
        positionField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(positionField);

        JLabel EvidenceLabel = new JLabel("Evidence : ");
        EvidenceLabel.setBounds(120, 250, 500, 50);
        panel.add(EvidenceLabel);

        JButton uploadButton = new JButton("Upload Image");
        uploadButton.setBounds(120, 290, 260, 50);
        uploadButton.setBackground(new Color(100, 149, 237)); // Warna biru
        uploadButton.setForeground(Color.WHITE);
        uploadButton.setFocusPainted(false);
        uploadButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(uploadButton);

        // Label untuk menampilkan nama file yang dipilih
        JLabel fileNameLabel = new JLabel("No file selected");
        fileNameLabel.setBounds(120, 350, 260, 30);
        fileNameLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        fileNameLabel.setForeground(Color.DARK_GRAY);
        panel.add(fileNameLabel);

        // Action listener untuk tombol upload
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select an Image");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "jpg", "png",
                    "jpeg", "bmp", "gif"));

            int userSelection = fileChooser.showOpenDialog(frame);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToUpload = fileChooser.getSelectedFile();
                fileNameLabel.setText(fileToUpload.getName()); // Tampilkan nama file di label
                filePath = fileToUpload.getAbsolutePath(); // Simpan path file
            }
        });

        JLabel updateLabel = new JLabel("Update By : ");
        updateLabel.setBounds(120, 370, 500, 50);
        panel.add(updateLabel);

        JTextField updateField = new JTextField(16);
        updateField.setHorizontalAlignment(JTextField.CENTER);
        updateField.setBounds(120, 410, 260, 50);
        updateField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(updateField);

        JComboBox<String> pilihTypestatus = new JComboBox<>(new String[] { "PENDING", "TRANSIT", "DELIVERED" });
        pilihTypestatus.setBounds(120, 480, 260, 40);
        pilihTypestatus.setFont(new Font("SansSerif", Font.PLAIN, 18));
        panel.add(pilihTypestatus);

        pilihTypestatus.setSelectedIndex(0);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(120, 550, 260, 50);
        Component.styleButton(saveButton, new Color(255, 69, 58), buttonFont);
        panel.add(saveButton);

        saveButton.addActionListener(e -> {
            String transIDs = transIDField.getText();
            String position = positionField.getText();
            String update = updateField.getText();
            Status status = Status.valueOf((String) pilihTypestatus.getSelectedItem());
            Date date = new Date(System.currentTimeMillis());

            if (!transIDs.isEmpty() && !position.isEmpty() && filePath != null && !filePath.isEmpty()
                    && !update.isEmpty()) {
                int transID = Integer.parseInt(transIDs);
                ShipmentDetail newShipmentDetail = new ShipmentDetail(0, transID, status, position, filePath, date,
                        update);

                boolean isCreated = TransaksiController.createShipmentDetail(newShipmentDetail, customer);

                if (isCreated) {
                    JOptionPane.showMessageDialog(frame, "Transaction berhasil dibuat");
                    frame.dispose();
                    new MenuCustomer();
                } else {
                    JOptionPane.showMessageDialog(frame, "Transaction gagal dibuat");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Harap isi semua field dan pilih file evidence!");
            }
        });

        JButton exitButton = new JButton("Back to Menu Customer");
        exitButton.setBounds(120, 620, 260, 50);
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
