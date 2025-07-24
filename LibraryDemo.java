import LibPack.*;
import java.util.*;

public class LibraryDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        Admin defaultAdmin = new Admin("Default Admin", "admin@library.com", "admin123", library);
        library.addUser(defaultAdmin);

        System.out.println("===== Welcome to Library Management System =====");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter Email: ");
                String email = sc.nextLine();
                System.out.print("Enter Password: ");
                String password = sc.nextLine();

                User user = library.getUser(email);

                if (user != null && user.authenticate(email, password)) {
                    System.out.println("Login successful! Logged in as: " + user.getRole());
                    user.showMenu(); 
                } else {
                    System.out.println("Invalid email or password.");
                }
            } else if (choice.equals("0")) {
                System.out.println("Exiting system. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        sc.close();
    }
}
