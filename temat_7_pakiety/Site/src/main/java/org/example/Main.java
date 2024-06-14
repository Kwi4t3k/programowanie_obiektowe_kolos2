package org.example;

import org.example.auth.Account;
import org.example.auth.AccountManager;
import org.example.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DatabaseConnection dbconnection = new DatabaseConnection();

        //zad 1
//        try {
//            dbconnection.connect("songs.db");
//
//            Connection connection = dbconnection.getConnection();
//
//            Statement statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM song");
//
//            while (resultSet.next()) {
//                System.out.println("ID: " + resultSet.getInt("id") + ", artist: " + resultSet.getString("artist") + ", title: " + resultSet.getString("title") + ", length: " + resultSet.getInt("length"));
//            }
//
//            dbconnection.disconnect();
//        } catch (SQLException e){
//            System.err.println(e.getMessage());
//        }

        //zad 2
        try {
            dbconnection.connect("my.db");

            AccountManager accountManager = new AccountManager(dbconnection);

            //rejestracja nowych użytkowników
            accountManager.register("user1", "qwert");
            accountManager.register("user2", "asdfg");

            //potwierdzenie tożsamości
            boolean isAuthenticated = accountManager.authenticate("user1", "qwert");
            System.out.println("Potwierdzenie user1: " + isAuthenticated);

            isAuthenticated = accountManager.authenticate("user2", "wrong_password");
            System.out.println("Potwierdzenie user2: " + isAuthenticated);

            //zebranie informacji
            Account account = accountManager.getAccount("user1");
            if(account != null){
                System.out.println("Otrzymane konto dla user1 " + account.name());
            }

            account = accountManager.getAccount(1);
            if(account != null){
                System.out.println("Otrzymane konto dla uzytkownika z id 1: " + account.name());
            }

            dbconnection.disconnect();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}