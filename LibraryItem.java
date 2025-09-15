abstract public class LibraryItem {
    private String title;
    private String author;
    private String ISBN;
    private boolean IsAvailable;
    private int quantity;
    private int availableQuantity;

    public LibraryItem(String Title, String Author, String ISBN_, int Quantity) {
        title = Title;
        author = Author;
        ISBN = ISBN_;
        IsAvailable = true;
        quantity = Quantity;
        availableQuantity = Quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public boolean isAvailable() {
        return IsAvailable;
    }

    public void set_status(boolean status) {
        IsAvailable = status;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void increase_quantity() {
        availableQuantity++;
    }

    public void decrease_quantity() {
        availableQuantity--;
    }

    public void returnItem() {
        if (availableQuantity < quantity) {
            IsAvailable = true;
            System.out.println("Item Returned");
            increase_quantity();
        }
    }


    public abstract boolean borrow();

}

