package org.example.music;

import org.example.database.DatabaseConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.AuthenticationException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ListnerAccountTest {
    // Metoda wywoływana przed wszystkimi testami, służy do otwarcia połączenia z bazą danych
    @BeforeAll
    static void openDatabase() throws SQLException {
        DatabaseConnection.connect("songs.db");
        ListenerAccount.Persistence.init();
    }

    // Metoda wywoływana po wszystkich testach, służy do zamknięcia połączenia z bazą danych
    @AfterAll
    static void closeDatabase(){
        DatabaseConnection.disconnect();
    }

    //Zapoznaj się z klasą ListenerAccount. Klasa rozszerza klasę Account o liczbę kredytów oraz listę piosenek posiadaną na koncie. Obie dane znajdują się wyłącznie w bazie danych. Napisz test sprawdzający poprawność rejestracji nowego konta.

    // Test sprawdzający poprawność rejestracji nowego konta
    @Test
    public void test_registerUser() throws SQLException {
        // Rejestracja nowego użytkownika
        int id = ListenerAccount.Persistence.register("Adam", "Mickiewicz");
        System.out.println(id);
        // Sprawdzenie, czy ID użytkownika nie jest zerem
        assertTrue(id != 0);
    }


    //Napisz test sprawdzający poprawne logowanie się do konta.
    // Test sprawdzający poprawne logowanie się do konta
    @Test
    public void test_loginUser() throws SQLException, AuthenticationException {
        // Rejestracja nowego użytkownika
        ListenerAccount.Persistence.register("Jan", "haslo");
        // Autentykacja użytkownika
        ListenerAccount account = ListenerAccount.Persistence.authenticate("Jan", "haslo");
        // Sprawdzenie, czy nazwa użytkownika jest taka sama jak podana przy rejestracji
        assertEquals("Jan", account.getUsername());
    }
}