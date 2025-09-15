public class Journal extends LibraryItem{
    public Journal(String title, String author,String ISBN, int quantity) {
        super(title, author, ISBN, quantity);
    }
    public boolean borrow() {
        System.out.println(getTitle()+" not available for borrowing!");
        return false;
    }
}
