package LibPack;
import java.util.*;
import java.text.SimpleDateFormat;

public class BorrowedBook {
    private Book book;
    private Date borrowDate;
    private int extensionCount;

    public BorrowedBook(Book book, Date borrowDate){
        this.book = book;
        this.borrowDate = borrowDate;
        this.extensionCount = 0;
    }

    public Book getBook(){
        return book;
    }

    public Date getBorrowDate(){
        return borrowDate;
    }

    public int getExtensionCount(){
        return extensionCount;
    }

    public void incrementExtension(){
        extensionCount++;
    }

    public String toRow(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%s | Borrowed On: %s | Extensions: %d" , book.getTitle(), sdf.format(borrowDate), extensionCount);
    }
}
