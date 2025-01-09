package view;

import controller.DatabaseHandler;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormDetailHistoryPaket extends JFrame {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    static DatabaseHandler conn = new DatabaseHandler();

    public FormDetailHistoryPaket(int transactionId) {
        // Frame setup
        setTitle("Shipment Details History");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel (using default layout and color)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Label
        JLabel titleLabel = new JLabel("Shipment Details History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create table
        String[] columns = { "Shipment ID", "Transaction ID", "Status", "Current Position", "Evidence", "Date",
                "Updated By" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFillsViewportHeight(true);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton BackButton = new JButton("Back To Menu Customer");
        BackButton.setFont(new Font("Arial", Font.BOLD, 14));
        BackButton.addActionListener(e -> {
            this.dispose();
            new MenuCustomer();
        });
        mainPanel.add(BackButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        loadShipmentDetails(transactionId);
    }

    private void loadShipmentDetails(int transactionId) {
        tableModel.setRowCount(0);

        try {
            conn.connect();

            String query = "SELECT id, transaction_id, status, current_position, evidence, date, updated_by FROM shipmentdetails WHERE transaction_id = ? ORDER BY date DESC";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setInt(1, transactionId); // Set transaction_id to filter
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getInt("transaction_id"),
                        rs.getString("status"),
                        rs.getString("current_position"),
                        rs.getString("evidence"),
                        rs.getDate("date"),
                        rs.getString("updated_by")
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.disconnect(); // Disconnect from database
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading shipment details: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
