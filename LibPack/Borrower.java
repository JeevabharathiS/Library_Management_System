package LibPack;
import java.util.*;

public class Borrower extends User{
    private double deposit;
    private Map<String ,BorrowedBook> borrowedBooks;
    private Library library;

    public Borrower(String name, String email, String password, Library library){
        super(name, email, password, "Borrower");
        this.library = library;
        this.deposit = 1500.0;
        this.borrowedBooks = new HashMap<>();
    }
    Scanner sc = new Scanner(System.in);

    @Override
    public void showMenu(){
        while(true){
            System.out.println("===== Borrower Menu =====");
            System.out.println("1. View Available Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Extend Book Tenure");
            System.out.println("5. Mark Book as Lost");
            System.out.println("6. Report Membership Card Lost");
            System.out.println("7. View Borrowed Books");
            System.out.println("0. Log Out");


            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAvailableBooks();
                    break;

                case 2:
                    borrowBook();
                    break;
            
                default:
                    break;
            }
        }
    }

    public void viewAvailableBooks(){
        List<Book> books = library.getAllBooks();
        System.out.println("----- Available Books -----");
        for(Book book: books){
            if(book.isAvailable()){
                System.out.println(book.getAsRow());
            }
        }
    }

    public void borrowBook(){
        if(borrowedBooks.size() >= 3){
            System.out.println("You Cant Borrow more than 3 books.");
            return;
        }

        System.out.print("Enter ISBN of the Book : ");
        String isbn = sc.nextLine();
        if(borrowedBooks.containsKey(isbn)){
            System.out.println("You have already Borrowed this Book.");
            return;
        }

        if (deposit < 500) {
            System.out.println("Minimum ₹500 deposit required to borrow a book.");
            return;
        }

        Book book = library.getBookByISBN(isbn);
        if (book == null || !book.isAvailable()) {
            System.out.println("Book not available.");
            return;
        }

        boolean success = book.borrowBook(email);
        if (success) {
            BorrowedBook record = new BorrowedBook(book, new Date());
            borrowedBooks.put(isbn, record);
            library.addToBorrowedHistory(email, book);
            System.out.println("Book borrowed successfully.");
        }
    }



    
}
