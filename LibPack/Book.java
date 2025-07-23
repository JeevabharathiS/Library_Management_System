package LibPack;
import java.util.*;

public class Book {
    private String title;
    private String isbn;
    private int quantity;
    private int timesBorrowed;
    private double cost;

    public Book(String title, String isbn, int quantity, double cost){
        this.title = title;
        this.isbn = isbn;
        this.quantity = quantity;
        this.cost = cost;
        this.timesBorrowed = 0;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public void setQuantity(int newQuantity){
        this.quantity = newQuantity;
    }

    public void setCost(double newCost){
        this.cost = newCost;
    }

    public boolean isAvailable(){
        return quantity>0;
    }

    public boolean borrowBook(String email){
        if(isAvailable()){
            quantity--;
            timesBorrowed++;
            return true;
        }
        return false;
    }

    public void returnBook(){
        quantity++;
    }

    
}
