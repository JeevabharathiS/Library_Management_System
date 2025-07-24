package LibPack;
import java.util.*;

import java.text.SimpleDateFormat;
import java.text.ParseException;

public class Utils {
    public static Date getValidDate(Scanner sc, String prompt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine();

            try {
                return sdf.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use DD/MM/YYYY.");
            }
        }
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    } 
}

