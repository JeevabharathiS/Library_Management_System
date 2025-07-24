package LibPack;
import java.util.*;
import java.text.SimpleDateFormat;

public class ReportGenerator {
    public static void booksWithLowStock(Library library, int threshold){
        System.out.println("----- Books With Quantity Below "+ threshold +" -----");
        for(Book b:library.getAllBooks()){
            if(b.getQuantity()<threshold){
                System.out.println(b.getAsRow());
            }
        }
    }

    public static void bookNeverBorrowed(Library library){
        System.out.println("----- Books Never Borrowed -----");
        for(Book b: library.getAllBooks()){
            if(b.getTimesBorrowed()==0){
                System.out.println(b.getAsRow());
            }
        }
    }

    public static void heavilyBorrowedBooks(Library library, int threshold){
        System.out.println("----- Books Borrowed More Than "+ threshold +" Times -----");
        for(Book b:library.getAllBooks()){
            if(b.getTimesBorrowed()>threshold){
                System.out.println(b.getAsRow());
            }
        }
    }

    public static void borrowersWithOverdue(Library library, Date date){
        System.out.println("Borrowers With Overdue Books (as of " + Utils.formatDate(date) + "):");

        for(User user: library.getAllUsers()){
            if(user instanceof Borrower){
                Borrower br = (Borrower) user;
                for(BorrowedBook bb: br.getBorrowedBooks().values()){
                    long diffInMs = date.getTime() - bb.getBorrowDate().getTime();
                    long diffInDays = diffInMs/(1000*60*60*24);

                    if(diffInDays > 15){
                        System.out.println(br.getName()+"   "+bb.getBook().getTitle()+"   Borrowed on: "+Utils.formatDate(bb.getBorrowDate())+"   Overdue by "+(diffInDays-15)+" days");
                    }
                }
            }
        }
    }

    public static void bookStatusByISBN(Library library, String isbn){
        Book book = library.getBookByISBN(isbn);
        if(book == null){
            System.out.println("No Such Book exists.");
            return;
        }

        System.out.println("Book Details : "+book.getTitle()+"["+isbn+"]");
        boolean flag = false;
        for(User user: library.getAllUsers()){
            if(user instanceof Borrower){
                Borrower br = (Borrower) user;
                if(br.getBorrowedBooks().containsKey(isbn)){
                    BorrowedBook bb = br.getBorrowedBooks().get(isbn);
                    System.out.println("Currently with: " + br.getName() + " (" + br.getEmail() + ")");
                    System.out.println("Borrowed on: " + Utils.formatDate(bb.getBorrowDate()));
                    System.out.println();
                    flag = true;
                }
            }
        }

        if(!flag){
            System.out.println("Currently Available in Rack");
        }        
    }

    public static void previousBorrowedBooks(Borrower br, Library library){
        List<Book> history = library.getBorrowedHistory(br.getEmail());
        if(history.isEmpty()){
            System.out.println("No Books Borrowed Yet");
        }else{
            System.out.println("----- Previously Borrowed Books by "+br.getName()+" -----");
            for(Book b: history){
                System.out.println(b.getAsRow());
            }
        }
    }

    public static void previousFines(Borrower br){
        List<FineRecord> history = br.getFineHistory();
        if(history.isEmpty()){
            System.out.println("No Fines Recorded");
        }else{
            System.out.println("----- Fine History of "+br.getName()+" -----");
            for(FineRecord f: history){
                System.out.println(f.getAsRow());
            }
        }
    }
}
