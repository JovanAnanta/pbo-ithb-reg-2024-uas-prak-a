package view;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import model.CurrentUser;
import model.Customer;

public class MenuCustomer {

    private JFrame frame;

    public MenuCustomer() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Customer customer = currentUser.getCustomer();
        showMenuCustomer(customer);
    }

    public void showMenuCustomer(Customer customer) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        final int FRAME_WIDTH = 500;
        final int FRAME_HEIGHT = 700;

        int start_x = screenWidth / 2 - (FRAME_WIDTH / 2);
        int start_y = screenHeight / 2 - (FRAME_HEIGHT / 2);

        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);

        frame = new JFrame("Homepage");
        frame.setUndecorated(true);
        frame.setBounds(start_x, start_y, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setShape(new RoundRectangle2D.Double(0, 0, FRAME_WIDTH, FRAME_HEIGHT, 30, 30));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(0.6f, 0.7f, 0.9f));
        panel.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);

        JButton transaksiPengirimanButton = new JButton("Tambah Transaksi Pengiriman");
        transaksiPengirimanButton.setBounds(120, 275, 260, 50);
        Component.styleButton(transaksiPengirimanButton, new Color(3, 123, 252), buttonFont);
        transaksiPengirimanButton.addActionListener(e -> {
            frame.dispose();
            new FormTambahTransaksi();
        });
        panel.add(transaksiPengirimanButton);

        JButton detailTransaksiPengirimanButton = new JButton("Tambah Detail Transaksi Pengiriman");
        detailTransaksiPengirimanButton.setBounds(120, 345, 260, 50);
        Component.styleButton(detailTransaksiPengirimanButton, new Color(3, 123, 252), buttonFont);
        detailTransaksiPengirimanButton.addActionListener(e -> {
            frame.dispose();
            new FormDetailTambahTransaksi();
        });
        panel.add(detailTransaksiPengirimanButton);

        JButton historyPaketButton = new JButton("History Paket");
        historyPaketButton.setBounds(120, 405, 260, 50);
        Component.styleButton(historyPaketButton, new Color(3, 123, 252), buttonFont);
        historyPaketButton.addActionListener(e -> {
            frame.dispose();
            new FormHistoryPaket();
        });
        panel.add(historyPaketButton);

        JButton exitButton = new JButton("Logout");
        exitButton.setBounds(120, 600, 260, 50);
        Component.styleButton(exitButton, new Color(255, 69, 58), buttonFont);
        panel.add(exitButton);

        exitButton.addActionListener(e -> {
            frame.dispose();
            CurrentUser.getInstance().clearUser();
            new MainMenu();
        });

        frame.add(panel);
        frame.setVisible(true);

    }
}