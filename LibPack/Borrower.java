package LibPack;
import java.text.SimpleDateFormat;
import java.util.*;

public class Borrower extends User{
    private double deposit;
    private Map<String ,BorrowedBook> borrowedBooks;
    private Library library;
    private List<FineRecord> fineHistory;

    public Borrower(String name, String email, String password, Library library){
        super(name, email, password, "Borrower");
        this.library = library;
        this.deposit = 1500.0;
        this.borrowedBooks = new HashMap<>();
        fineHistory = new ArrayList<>();
    }
    Scanner sc = new Scanner(System.in);

    @Override
    public void showMenu(){
        while(true){
            System.out.println();
            System.out.println("===== Borrower Menu =====");
            System.out.println("1. View Available Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Extend Book Tenure");
            System.out.println("5. Mark Book as Lost");
            System.out.println("6. Report Membership Card Lost");
            System.out.println("7. View Borrowed Books");
            System.out.println("8. View Previous Fines");
            System.out.println("9. View Previous Books");
            System.out.println("0. Log Out");
            System.out.println();

            System.out.print("Enter Your Choice : ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewAvailableBooks();
                    break;

                case 2:
                    borrowBook();
                    break;

                case 3:
                    System.out.print("Enter the Book's ISBN to return : ");
                    String r_isbn = sc.nextLine();
                    returnBook(r_isbn);
                    break;

                case 4: 
                    System.out.print("Enter the Book's ISBN : ");
                    String e_isbn = sc.nextLine();
                    extendTenure(e_isbn);
                    break;

                case 5:
                    System.out.print("Enter the Book's ISBN : ");
                    String l_isbn = sc.nextLine();
                    markBookAsLost(l_isbn);
                    break;

                case 6:
                    membershipCardLost();
                    break;

                case 7:
                    viewBorrowedBooks();
                    break;

                case 8:
                    ReportGenerator.previousFines(this);
                    break;
                
                case 9:
                    ReportGenerator.previousBorrowedBooks(this, library);
                    break;

                case 0:
                    System.out.println("Logging Out...");
                    return;
            
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

        boolean success = book.borrowBook();
        if (success) {
            BorrowedBook record = new BorrowedBook(book, new Date());
            borrowedBooks.put(isbn, record);
            library.addToBorrowedHistory(email, book);
            System.out.println("Book borrowed successfully.");
        }
    }

    public void returnBook(String isbn){
        BorrowedBook bb = borrowedBooks.get(isbn);
        if(bb == null){
            System.out.println("You Haven't Borrowed that Book");
            return;
        }
        else{
            
            Book book = bb.getBook();
            
            Date returnDate = Utils.getValidDate(sc, "Enter Return Date (DD/MM/YYYY): ");
            // SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date borrowedDate = bb.getBorrowDate();

            double fine = FineCalc.calcFine(book, borrowedDate, returnDate);
            if(fine>0){
                System.out.println("Late return fine: ₹" + fine);
                deductFineFromDeposit(fine,"Late Return of Book :"+book.getTitle());
            }

            book.returnBook();
            borrowedBooks.remove(isbn);
            System.out.println("Book returned Successfully.");
        }
    }

    public void extendTenure(String isbn){
        BorrowedBook bb = borrowedBooks.get(isbn);
        if(bb == null){
            System.out.println("You Haven't Borrowed that Book.");
            return;
        }else{
            if(bb.getExtensionCount() >= 2){
                System.out.println("You Have Reached the Maximum Tensure-Extension Limit.");
                return;
            }else{
                bb.incrementExtension();
                System.out.println("Tenure Extended Successfully.");
            }
        }
    }

    public void markBookAsLost(String isbn){
        BorrowedBook bb = borrowedBooks.get(isbn);
        if(bb == null){
            System.out.println("You Haven't Borrowed that Book.");
            return;
        }else{
            Book book = bb.getBook();
            double fine = book.getCost() * 0.5;
            System.out.println("The Book is Marked as Lost. Fine : ₹"+ fine);
            deductFineFromDeposit(fine, "Book Lost.");
            borrowedBooks.remove(isbn);
        }
    }

    public void deductFineFromDeposit(double amount, String reason){
        Date today = new Date();
        fineHistory.add(new FineRecord(reason, amount, today));

        if(deposit >= amount){
            deposit -= amount;
        }else{
            System.out.println("You have Insufficient Deposit. You need to pay the Remaining of ₹" + (amount - deposit));
            deposit = 0;
            return;
        }
    }

    public void membershipCardLost(){
        System.out.println("Reported that you have Lost Membership Card. You need to pay the fine of ₹10");
        deductFineFromDeposit(10.0, "Membership Card Lost");
        return;
    }

    public void viewBorrowedBooks(){
        List<BorrowedBook> bbs = new ArrayList<>(borrowedBooks.values());
        System.out.println("----- Available Books -----");
        for(BorrowedBook bb: bbs){
            System.out.println(bb.toRow());
        }
    }

    public List<FineRecord> getFineHistory(){
        return fineHistory;
    }

    public Map<String,BorrowedBook> getBorrowedBooks(){
        return borrowedBooks;
    }
}
