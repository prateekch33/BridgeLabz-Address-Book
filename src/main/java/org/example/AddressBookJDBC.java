package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class AddressBookJDBC {
    public Connection connectionManager() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/addressbook";
        String userName = "root";
        String password = "PrateekC@20035";
        return DriverManager.getConnection(jdbcURL, userName, password);
    }

    public void addContact(String name, String address, String city, String state, String zip) {
        Connection conn = null;
        PreparedStatement pstmt = null;
    
        try {
            conn = connectionManager();

            String sql = "INSERT INTO entries (name, address, city, state, zip) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, name);
            pstmt.setString(2, address);
            pstmt.setString(3, city);
            pstmt.setString(4, state);
            pstmt.setString(5, zip);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void retrieveAllEntries() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = connectionManager();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM entries");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("City: " + rs.getString("city"));
                System.out.println("State: " + rs.getString("state"));
                System.out.println("Zip: " + rs.getString("zip"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateContact(String name, String newAddress, String newCity, String newState, String newZip) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = connectionManager();
            String sql = "UPDATE entries SET address = ?, city = ?, state = ?, zip = ? WHERE name = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, newAddress);
            pstmt.setString(2, newCity);
            pstmt.setString(3, newState);
            pstmt.setString(4, newZip);
            pstmt.setString(5, name);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void retrieveContactsAddedInPeriod(Date startDate, Date endDate) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionManager();

            String sql = "SELECT * FROM entries WHERE added_date BETWEEN ? AND ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Address: " + rs.getString("address"));
                System.out.println("City: " + rs.getString("city"));
                System.out.println("State: " + rs.getString("state"));
                System.out.println("Zip: " + rs.getString("zip"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void retrieveContactsCountByCityOrState(String city, String state) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = connectionManager();

            String sql = "SELECT COUNT(*) FROM entries WHERE city = ? OR state = ?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, city);
            pstmt.setString(2, state);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Number of contacts: " + rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
