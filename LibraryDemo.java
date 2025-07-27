import LibPack.*;
import java.util.*;

public class LibraryDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        library.addUser(new Admin("admin1", "admin1@gmail.com", "admin1pass", library));
        library.addUser(new Admin("admin2", "admin2@gmail.com", "admin2pass", library));

        System.out.println("===== Welcome to Library Management System =====");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("0. Exit");
            System.out.println();
            int choice = Utils.getValidInteger(sc, "Enter Your Choice : ");

            if (choice==1) {
                System.out.println();
                String email = Utils.getValidString(sc, "Enter Email : ");
                String password = Utils.getValidString(sc, "Enter Password: ");

                User user = library.getUser(email);
                System.out.println();
                if (user != null && user.authenticate(email, password)) {
                    System.out.println("Login successful! Logged in as: " + user.getRole());
                    user.showMenu(); 
                } else {
                    System.out.println("Invalid email or password.");
                }
            } else if (choice==0) {
                System.out.println("Exiting system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}
