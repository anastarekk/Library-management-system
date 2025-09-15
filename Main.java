import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Library lib = new Library();// Create a Library instance

        LibraryUser user1 = new LibraryUser("Ahmed Ali", "1001");
        LibraryUser user2 = new LibraryUser("Sara Mohamed", "1002");
        LibraryUser user3 = new LibraryUser("Omar Hassan", "1003");
        LibraryUser user4 = new LibraryUser("Fatma Youssef", "1004");
        lib.assignUser(user1);
        lib.assignUser(user2);
        lib.assignUser(user3);
        lib.assignUser(user4);
        // Launch Swing GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI(lib);
        });
    }
}
