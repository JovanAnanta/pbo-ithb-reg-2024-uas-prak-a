package view;

import controller.DatabaseHandler;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormHistoryPaket extends JFrame {
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    static DatabaseHandler conn = new DatabaseHandler();

    public FormHistoryPaket() {
        // Frame setup
        setTitle("Transaction History");
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Main panel (using default layout and color)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Label
        JLabel titleLabel = new JLabel("Transaction History", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create table
        String[] columns = { "Transaction ID", "Package Type", "Package Weight", "Total Cost", "Created At" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setFillsViewportHeight(true);
        transactionTable.setFont(new Font("Arial", Font.PLAIN, 14));

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Detail button
        JButton detailButton = new JButton("View Shipment Details");
        detailButton.setFont(new Font("Arial", Font.BOLD, 14));
        detailButton.addActionListener(e -> {
            this.dispose();
            openDetailHistoryPaket();
        });
        mainPanel.add(detailButton, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);

        // Load transactions when frame opens
        loadTransactions();
    }

    private void loadTransactions() {
        // Clear existing table data
        tableModel.setRowCount(0);

        try {
            conn.connect();

            String query = "SELECT id, package_type, package_weight, total_cost, created_at FROM transaction ORDER BY created_at DESC";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),
                        rs.getString("package_type"),
                        rs.getDouble("package_weight"),
                        String.format("$%.2f", rs.getDouble("total_cost")),
                        rs.getDate("created_at")
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
            conn.disconnect(); // Disconnect from database
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Error loading transactions: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openDetailHistoryPaket() {
        int selectedRow = transactionTable.getSelectedRow();
        if (selectedRow != -1) {
            int transactionId = (int) transactionTable.getValueAt(selectedRow, 0);
            new FormDetailHistoryPaket(transactionId); // Pass transaction ID to the detail form
        } else {
            JOptionPane.showMessageDialog(this, "Please select a transaction first.");
            new FormHistoryPaket();
        }
    }
}
