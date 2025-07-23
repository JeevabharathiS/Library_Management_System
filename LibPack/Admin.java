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
            System.out.println("===== Admin Menu =====");
            System.out.println("1. Add Book");
            System.out.println("2. Modify Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View Book by Title");
            System.out.println("5. View Book by Quantity");
            System.out.println("6. Search Book by Name");
            System.out.println("7. View Book by ISBN");
            System.out.println("8. Add Borrower");
            System.out.println("9. Add Admin");
            System.out.println("0. Log Out");
            
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter the Title : ");
                    String title = sc.nextLine();
                    System.out.print("Enter the ISBN : ");
                    String isbn = sc.nextLine();
                    System.out.print("Enter the Quantity : ");
                    int quantity = sc.nextInt();
                    System.out.print("Enter the Cost : ");
                    double cost = sc.nextDouble();
                    addBook(new Book(title, isbn, quantity, cost));
                    break;
                
                case 2:
                    System.out.print("Enter ISBN : ");
                    String mod_isbn = sc.nextLine();
                    System.out.print("Enter New Quantity : ");
                    int newQuantity = sc.nextInt();
                    System.out.print("Enter New Cost : ");
                    double newCost = sc.nextDouble();
                    modifyBook(mod_isbn, newQuantity, newCost);
                    break; 

                case 3:
                    System.out.print("Enter ISBN : ");
                    String delIsbn = sc.nextLine();
                    deleteBook(delIsbn);
                    break;
                
                case 4:
                    viewBookByName();
                    break;
                
                case 5:
                    viewBookByQuantity();
                    break;

                case 6:
                    System.out.print("Enter Book Name : ");
                    searchBookByName(sc.nextLine());
                    break;
                
                case 7:
                    System.out.print("Enter ISBN : ");
                    searchBookByISBN(sc.nextLine());
                    break;
                
                case 8: 
                    System.out.print("Enter Borrower Name : ");
                    String b_name = sc.nextLine();
                    System.out.print("Enter Borrower Email : ");
                    String b_mail = sc.nextLine();
                    System.out.print("Enter Password : ");
                    String b_password = sc.nextLine();
                    addBorrower(new Borrower(b_name, b_mail, b_password, library));
                    break;

                case 9:
                    System.out.print("Enter Admin Name : ");
                    String a_name = sc.nextLine();
                    System.out.print("Enter Admin Email : ");
                    String a_mail = sc.nextLine();
                    System.out.print("Enter Password : ");
                    String a_password = sc.nextLine();
                    addAdmin(new Admin(a_name, a_mail, a_password, library));
                    break;

                case 0:
                    return;

                default:
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
