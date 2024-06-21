package org.example.auth;

// Klasa Account reprezentuje konto użytkownika.
public record Account(int id, String name) {
    // Istniejące komentarze:
    //    private static int id;
    //    private static String name;
    //
    //    public Account(int id, String name){
    //        this.id = id;
    //        this.name = name;
    //    }

    // Nowe komentarze:
    // Klasa rekordu Account posiada dwa pola: id i name.
    // Id to unikalny identyfikator dla każdego konta.
    // Name to nazwa konta.
}
