package LibPack;
import java.util.*;

public class Admin extends User {
    private Library library;

    public Admin(String name, String email, String password, Library library){
        super(name, email, password, "Admin");
        this.library = library;
    }


    @Override
    public void showMenu(){
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println();
            System.out.println("===== Admin Menu =====");
            System.out.println("\n1. Add Book");
            System.out.println("2. Modify Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View Book by Title");
            System.out.println("5. View Book by Quantity");
            System.out.println("6. Search Book by Name");
            System.out.println("7. View Book by ISBN");
            System.out.println("8. Add Borrower");
            System.out.println("9. Add Admin");
            System.out.println("10. View Books with Low Stock");
            System.out.println("11. View Books That Never Borrowed");
            System.out.println("12. View Heavily Borrowed Books");
            System.out.println("13. View Borrowers with Overdue");
            System.out.println("14. View Book Status by ISBN");
            System.out.println("0. Log Out");

            System.out.println();
            int choice = Utils.getValidInteger(sc, "Enter Your Choice : ");

            switch (choice) {
                case 1:
                    System.out.println();
                    String title = Utils.getValidString(sc, "Enter the Title : ");
                    String isbn = Utils.getValidString(sc, "Enter the ISBN : ");
                    int quantity = Utils.getValidInteger(sc, "Enter the Quantity : ");
                    double cost = Utils.getValidDouble(sc, "Enter the Cost : ");
                    addBook(new Book(title, isbn, quantity, cost));
                    break;
                
                case 2:
                    System.out.println();
                    String mod_isbn = Utils.getValidString(sc, "Enter the ISBN : ");
                    int newQuantity = Utils.getValidInteger(sc, "Enter New Quantity : ");
                    double newCost = Utils.getValidDouble(sc, "Enter New Cost : ");
                    modifyBook(mod_isbn, newQuantity, newCost);
                    break; 

                case 3:
                    System.out.println();
                    String delIsbn = Utils.getValidString(sc, "Enter the ISBN : ");
                    deleteBook(delIsbn);
                    break;
                
                case 4:
                    System.out.println();
                    viewBookByName();
                    break;
                
                case 5:
                    System.out.println();
                    viewBookByQuantity();
                    break;

                case 6:
                    System.out.println();
                    String bookName = Utils.getValidString(sc, "Enter Book Name : ");
                    searchBookByName(bookName);
                    break;
                
                case 7:
                    System.out.println();
                    searchBookByISBN(Utils.getValidString(sc, "Enter the ISBN : "));
                    break;
                
                case 8: 
                    System.out.println();
                    String b_name = Utils.getValidString(sc, "Enter Borrower Name : ");
                    String b_mail = Utils.getValidString(sc, "Enter Borrower Email : ");
                    String b_password = Utils.getValidString(sc, "Enter Password : ");
                    addBorrower(new Borrower(b_name, b_mail, b_password, library));
                    break;

                case 9:
                    System.out.println();
                    String a_name = Utils.getValidString(sc, "Enter Admin Name : ");
                    String a_mail = Utils.getValidString(sc, "Enter Admin Email : ");
                    String a_password = Utils.getValidString(sc, "Enter Password : ");
                    addAdmin(new Admin(a_name, a_mail, a_password, library));
                    break;
                
                case 10:
                    System.out.println();
                    int threshold = Utils.getValidInteger(sc, "Enter the Threshold Value : ");
                    ReportGenerator.booksWithLowStock(library, threshold);
                    break;

                case 11:
                    System.out.println();
                    ReportGenerator.bookNeverBorrowed(library);
                    break;
                
                case 12:
                    System.out.println();
                    int thresh= Utils.getValidInteger(sc, "Enter the Threshold Value : ");
                    ReportGenerator.heavilyBorrowedBooks(library, thresh);
                    break;
                
                case 13:
                    System.out.println();
                    Date date = Utils.getValidDate(sc, "Enter date to Check Overdue (DD/MM/YYYY) : ");
                    ReportGenerator.borrowersWithOverdue(library, date);
                    break;
                
                case 14:
                    System.out.println();
                    String isbn_status = Utils.getValidString(sc, "Enter the ISBN : ");
                    ReportGenerator.bookStatusByISBN(library, isbn_status);
                    break;

                case 0:
                    System.out.println();
                    System.out.println("Logging Out...");
                    return;

                default:
                    System.out.println();
                    System.out.println("Invalid Choice !");
            }
        }
    }

    public void addBook(Book book){
        library.addBook(book);
        System.out.println("Book added successfully.");
    }

    public void modifyBook(String mod_isbn, int newQuantity, double newCost){
        Book book =library.getBookByISBN(mod_isbn);
        if(book != null){
            book.setQuantity(newQuantity);
            book.setCost(newCost);
            System.out.println("Book Updated");
        }else{
            System.out.println("Book Not Found");
        }
    }

    public void deleteBook(String isbn){
        boolean removed = library.deleteBook(isbn);
        if(removed){
            System.out.println("Book Deleted.");
        }else{
            System.out.println("Book Not Found.");
        }
    }

    public void viewBookByName(){
        List<Book> books = library.getAllBooks();
        books.sort((b1,b2) -> b1.getTitle().compareTo(b2.getTitle()));
        System.out.println("----- Books Sorted By Name -----");
        for(Book b: books){
            System.out.println(b.getAsRow());
        }
    }

    public void viewBookByQuantity(){
        List<Book> books = library.getAllBooks();
        books.sort((b1,b2)-> b1.getQuantity() - b2.getQuantity());
        System.out.println("----- Books Sorted By Quantity -----");
        for(Book b: books){
            System.out.println(b.getAsRow());
        }
    }

    public void searchBookByName(String name){
        List<Book> books = library.searchBookByName(name);
        if(books.isEmpty()){
            System.out.println("No Books Found with that Name");
        }else{
            System.out.println("----- Books with ("+ name +") Name -----");
            for(Book b: books){
                System.out.println(b.getAsRow());
            }
        }
    }

    public void searchBookByISBN(String isbn){
        Book book = library.searchBookByISBN(isbn);
        if(book != null){
            System.out.println(book.toString());
        }else{
            System.out.println("No Such Book Exits.");
        }
    }

    public void addBorrower(Borrower br){
        library.addUser(br);
        System.out.println("Borrower Added.");
    }

    public void addAdmin(Admin ad){
        library.addUser(ad);
        System.out.println("Admin Added.");
    }
}
