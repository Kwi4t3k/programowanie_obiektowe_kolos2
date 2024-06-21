public class Main {
    public static void main(String[] args) {
        WordBag wordBag = new WordBag(); // Tworzenie nowego obiektu WordBag
        wordBag.populate(); // Wypełnianie worka słowami
        Server server = new Server(5000, wordBag); // Tworzenie nowego serwera na porcie 5000 z workiem słów
        server.start(); // Uruchamianie serwera
        server.startSending(); // Rozpoczęcie wysyłania słów przez serwer
    }
}
