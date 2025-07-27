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

    public static String getValidString(Scanner sc, String prompt){
        while(true){
            try{
                System.out.print(prompt);
                String input = sc.nextLine();
                return input;
            } catch(Exception e){
                System.out.println("Invalid Data Type. Please Enter Correctly");
            }
        }
    }

    public static int getValidInteger(Scanner sc, String prompt){
        while(true){
            try{
                System.out.print(prompt);
                int input = sc.nextInt();
                sc.nextLine();
                return input;
            }catch(Exception e){
                System.out.println("Invalid Data Type. Please Enter Correctly");
                sc.nextLine();
            }
        }
    }

    public static double getValidDouble(Scanner sc, String prompt){
        while(true){
            try{
                System.out.print(prompt);
                double input = sc.nextDouble();
                sc.nextLine();
                return input;
            }catch(Exception e){
                System.out.println("Invalid Data Type. Please Enter Correctly");
                sc.nextLine();
            }
        }
    }

    


}

