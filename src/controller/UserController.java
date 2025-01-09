package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.CurrentUser;
import model.Customer;

public class UserController {
    private static UserController instance;
    static DatabaseHandler conn = new DatabaseHandler();

    public static UserController getInstance() {
        if (instance == null) {
            synchronized (UserController.class) {

                if (instance == null) {
                    instance = new UserController();
                }
            }
        }
        return instance;
    }

    public static Customer verifyUser(String username, String password) {
        try {
            conn.connect();
            String query = "SELECT * FROM customer WHERE phone = ? AND password = ?";
            PreparedStatement stmt = conn.con.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Customer loggedInUser = null;

                loggedInUser = fetchNasabah(rs);

                if (loggedInUser != null) {
                    CurrentUser.getInstance().setCustomer(loggedInUser);
                }
                return loggedInUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Customer fetchNasabah(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("id"), // Ambil ID dari ResultSet
                rs.getString("password"),
                rs.getString("name"),
                rs.getString("address"),
                rs.getString("phone"));
    }

    public static boolean verifyRegister(String phone, String password, String name, String address) {
        try {
            conn.connect();

            String checkQuery = "SELECT * FROM customer WHERE phone = ? OR password = ?";
            PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
            checkStmt.setString(1, phone);
            checkStmt.setString(2, password);

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            boolean cekPhoneAndName = cekNomorDiDB(phone, name);

            if (!cekPhoneAndName) {
                return false;
            }

            String insertQuery = "INSERT INTO customer (id, password, name, address, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.con.prepareStatement(insertQuery);
            int userUniqueId = generateId();
            insertStmt.setInt(1, userUniqueId);
            insertStmt.setString(2, password);
            insertStmt.setString(3, name);
            insertStmt.setString(4, address);
            insertStmt.setString(5, phone);

            int rowsInserted = insertStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean cekNomorDiDB(String phone, String name) {
        try {
            conn.connect();

            String checkQuery = "SELECT 1 FROM customer WHERE phone = ? AND name = ?";
            PreparedStatement checkStmt = conn.con.prepareStatement(checkQuery);
            checkStmt.setString(1, phone);
            checkStmt.setString(2, name);

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                return false;
            }

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Integer generateId() {
        conn.connect();
        int nextId = 1;

        try {
            String query = "SELECT id FROM customer ORDER BY id DESC LIMIT 1";
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
