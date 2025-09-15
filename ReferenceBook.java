public class ReferenceBook extends LibraryItem {
    public ReferenceBook(String title, String author, String ISBN, int quantity) {
        super(title, author, ISBN, quantity);
    }


    public boolean borrow() {
        System.out.println(getTitle()+" not available for borrowing!");
        return false ;
    }
}
