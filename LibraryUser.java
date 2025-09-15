import java.util.ArrayList;

public class LibraryUser {
    private String name;
    private String userID;
    private ArrayList<LibraryItem>  borrowedItems;
    public LibraryUser(String Name, String UserID) {
        name = Name;
        userID = UserID;
        borrowedItems = new ArrayList<>();
    }
    public void borrowItem(LibraryItem item) {
        boolean flag = item.borrow();
        if(flag) {
            borrowedItems.add(item);
        }
    }
    public boolean returnItem(LibraryItem item) {
        if(borrowedItems.contains(item)) {
            borrowedItems.remove(item);
            item.returnItem();
            return true;
        }
        else{
            return false;
        }
    }
    public String getid() {
        return userID;
    }
}
