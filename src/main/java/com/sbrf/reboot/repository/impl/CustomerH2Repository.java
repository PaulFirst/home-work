package com.sbrf.reboot.repository.impl;

import com.sbrf.reboot.dto.Customer;
import com.sbrf.reboot.repository.CustomerRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerH2Repository implements CustomerRepository {

    private final String JDBC_DRIVER = "org.h2.Driver";
    private final String DB_URL = "jdbc:h2:~/my_db";

    private final String USER = "sa";
    private final String PASS = "";

    public CustomerH2Repository() {

        String sql = "CREATE TABLE IF NOT EXISTS CUSTOMER " +
                "(id INTEGER IDENTITY(1,1), " +
                "name VARCHAR(255), " +
                "email VARCHAR(255))";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM CUSTOMER";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong("id"));
                customer.setName(resultSet.getString("name"));
                customer.setEMail(resultSet.getString("email"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    @Override
    public boolean createCustomer(String name, String eMail) {
        String sql = "INSERT INTO CUSTOMER " +
                "(name, email) VALUES (?, ?)";
        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, name);
            stmt.setString(2, eMail);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteCustomer(String userName, String eMail) {
        String sql = "DELETE FROM CUSTOMER " +
                "WHERE name = ? AND email = ?";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, userName);
            stmt.setString(2, eMail);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateCustomer(String newUserName, String newEMail, String oldUserName, String oldEMail) {
        String sql = "UPDATE CUSTOMER " +
                "SET name = ?, email = ? " +
                "WHERE name = ? AND email = ?";

        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, newUserName);
            stmt.setString(2, newEMail);
            stmt.setString(3, oldUserName);
            stmt.setString(4, oldEMail);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}


