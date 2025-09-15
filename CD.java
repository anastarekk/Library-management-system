public class CD extends LibraryItem implements Borrowable{
    private final int Duration = 7; // 7 days
    public CD(String title, String author,String ISBN, int quantity) {
        super(title, author, ISBN, quantity);
    }


    public boolean borrow() {
        if (isAvailable() && getAvailableQuantity() > 0) {
            System.out.println(getTitle() + " borrowed for " + Duration + " days");
            decrease_quantity();
            if (getAvailableQuantity() == 0) {
                set_status(false);
            }
            return true;
        } else {
            System.out.println(getTitle() + " is not available for borrowing. Please come back again after " + Duration + " days");
            return false;
        }
    }
}
