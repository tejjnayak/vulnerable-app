package com.vulnerableapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@RestController
public class InsecureController {

    @Autowired
    private DataSource dataSource;

    // Insecure endpoint vulnerable to SQL Injection
    @GetMapping("/search")
    public String search(@RequestParam String query) {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM users WHERE username = '" + query + "'"; // Vulnerable to SQL injection
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return "User found: " + resultSet.getString("username");
            } else {
                return "No user found";
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
