package LibPack;
import java.util.*; 

public class Library {
    private Map<String, User> users;
    private Map<String, Book> books;
    private Map<String, List<Book>> borrowRecords;

    public Library(){
        users = new HashMap<>();
        books = new HashMap<>();
        borrowRecords = new HashMap<>();
    }

    public void addUser(User user){
        users.put(user.getEmail(), user);
    }

    public User getUser(String email){
        return users.get(email);
    }

    public Borrower getBorrower(String email){
        User user = users.get(email);
        if(user instanceof Borrower){
            return (Borrower) user;
        }
        return null;
    }

    public boolean emailExists(String email){
        return users.containsKey(email);
    }

    public void addBook(Book book){
        books.put(book.getISBN(), book);
    }

    public boolean deleteBook(String isbn){
        return books.remove(isbn)!=null;
    }

    public Book getBookByISBN(String isbn){
        return books.get(isbn);
    }

    public List<Book> getAllBooks(){
        return new ArrayList<>(books.values());
    }

    public List<Book> searchBookByName(String name){
        List<Book> res = new ArrayList<>();
        for(Book b: books.values()){
            if(b.getTitle().toLowerCase().contains(name.toLowerCase())){
                res.add(b);
            }
        }
        return res;
    }

    public Book searchBookByISBN(String isbn){
        return books.get(isbn);
    }

    public void addToBorrowedHistory(String email, Book book){
        borrowRecords.computeIfAbsent(email, k -> new ArrayList<>());
        borrowRecords.get(email).add(book);
    }

    public List<Book> getBorrowedHistory(String email){
        return borrowRecords.getOrDefault(email, new ArrayList<>());
    }
}
