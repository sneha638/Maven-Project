package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRegistration {

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/user_db", "root", "Sneha123*");
    }
    public void registerUser(String name, String username, String password) {
        try (Connection connection = getConnection()) {
            String checkUserQuery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("Username already exists!");
                return;
            }
            String query = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
            System.out.println("Registration successful!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void loginUser(String username, String password) {
        try (Connection connection = getConnection()) {
            String query = "SELECT name FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                System.out.println("Login successful! Welcome " + name + "!");
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
