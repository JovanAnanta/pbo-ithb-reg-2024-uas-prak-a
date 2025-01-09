package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Customer;
import model.ShipmentDetail;
import model.Transaction;

public class TransaksiController {
    static DatabaseHandler conn = new DatabaseHandler();

    public static boolean createTransaction(Transaction transaction, Customer customer) {
        conn.connect();

        try {
            String query = "INSERT INTO transaction (id, customer_id, package_type, package_weight, total_cost, created_at, receipt_name, receipt_address, receipt_phone)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            int transaction_id = generateId();
            stmt.setInt(1, transaction_id);
            stmt.setInt(2, customer.getId());
            stmt.setString(3, transaction.getPackageType());
            stmt.setDouble(4, transaction.getPackageWeight());
            stmt.setDouble(5, transaction.getTotalCost());
            stmt.setDate(6, transaction.getCreatedAt());
            stmt.setString(7, transaction.getReceiptName());
            stmt.setString(8, transaction.getReceiptAddress());
            stmt.setString(9, transaction.getReceiptPhone());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Integer generateId() {
        conn.connect();
        int nextId = 1;

        try {
            String query = "SELECT id FROM transaction ORDER BY id DESC LIMIT 1";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nextId = rs.getInt("id") + 1;
            }

            return nextId;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean createShipmentDetail(ShipmentDetail newShipmentDetail, Customer customer) {
        conn.connect();

        try {
            String query = "INSERT INTO shipmentdetails (id, transaction_id, status, current_position, evidence, date, updated_by)"
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            int ship_id = generateIdShip();
            stmt.setInt(1, ship_id);
            stmt.setInt(2, newShipmentDetail.getTransactionId());
            stmt.setString(3, newShipmentDetail.getStatus().name());
            stmt.setString(4, newShipmentDetail.getCurrentPosition());
            stmt.setString(5, newShipmentDetail.getEvidence());
            stmt.setDate(6, newShipmentDetail.getDate());
            stmt.setString(7, newShipmentDetail.getUpdatedBy());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Integer generateIdShip() {
        conn.connect();
        int nextId = 1;

        try {
            String query = "SELECT id FROM shipmentdetails ORDER BY id DESC LIMIT 1";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nextId = rs.getInt("id") + 1;
            }

            return nextId;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
