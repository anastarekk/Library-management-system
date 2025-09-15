public class Ebook extends LibraryItem implements Borrowable{
    private final int Duration = 7; //7 days

    public Ebook(String title, String author, String ISBN, int quantity) {
        super(title, author, ISBN, quantity);
    }

   public boolean borrow() {
       System.out.println(getTitle()+" borrowed successfully! expires automatically after "+Duration+" days");
       return true ;
   }
}
