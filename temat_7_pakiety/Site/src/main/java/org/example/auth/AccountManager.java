package org.example.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManager {
    private final DatabaseConnection dbConnection;

    public AccountManager(DatabaseConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void register(String username, String password) {
        try {
            String hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
            String insertSQL = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement statement = dbConnection.getConnection().prepareStatement(insertSQL);

            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean authenticate(String username, String password) throws SQLException {
        String querySQL = "SELECT password FROM account WHERE username = ?;";
        PreparedStatement statement = dbConnection.getConnection().prepareStatement(querySQL);

        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String hashedPassword = resultSet.getString("password");
            BCrypt.Result cryptResult = BCrypt.verifyer().verify(password.toCharArray(), hashedPassword);
            return cryptResult.verified;
        } else {
            throw new RuntimeException("No such user " + username);
        }
    }

    public Account getAccount(String username) throws SQLException {
        String querySQL = "SELECT id, username FROM account WHERE username = ?;";
        PreparedStatement statement = dbConnection.getConnection().prepareStatement(querySQL);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            return new Account(id, username);
        } else {
            throw new RuntimeException("No such user " + username);
        }
    }

    public Account getAccount(int id) throws SQLException {
        String querySQL = "SELECT id, username FROM account WHERE id = ?;";
        PreparedStatement statement = dbConnection.getConnection().prepareStatement(querySQL);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("username");
            return new Account(id, name);
        } else {
            throw new RuntimeException("No such user with ID " + id);
        }
    }
}
