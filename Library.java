import java.util.HashMap;

public class Library {
    private HashMap<String,LibraryItem> items;
    private HashMap<String,LibraryUser> users;
    public Library() {
        items = new HashMap<>();
        users = new HashMap<>();
        users.put("1001", new LibraryUser("1001", "Ahmed Ali"));
        users.put("1002", new LibraryUser("1002", "Sara Mohamed"));
        users.put("1003", new LibraryUser("1003", "Omar Hassan"));
        users.put("1004", new LibraryUser("1004", "Fatma Youssef"));

    }
    public void addItem(LibraryItem item) {
        items.put(item.getISBN(), item);
        item.increase_quantity();
        System.out.println(item.getTitle() + " added to the library successfully!");
    }
    public String Print() { // List all available items
        StringBuilder sb = new StringBuilder();

        for (LibraryItem item : items.values()) {
            if (item.isAvailable() && item.getAvailableQuantity() > 0) {
                sb.append("Title: ").append(item.getTitle()).append("\n")
                        .append("✍Author: ").append(item.getAuthor()).append("\n")
                        .append("ISBN: ").append(item.getISBN()).append("\n")
                        .append("Copies: ").append(item.getAvailableQuantity()).append("\n")
                        .append("-------------------------------------------------------\n");
            }
        }

        return sb.length() > 0 ? sb.toString() : "No books available.";
    }

    public LibraryUser getUser(String userID){
     return users.get(userID);
    }
    public LibraryItem getItem(String isbn){
        return items.get(isbn);
    }
    public void assignUser(LibraryUser user){
        users.put(user.getid(),user);
    }
}
