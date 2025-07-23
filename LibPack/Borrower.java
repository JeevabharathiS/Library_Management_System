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

    @Override
    public void showMenu(){
        
    }
    
}
