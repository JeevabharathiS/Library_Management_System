package LibPack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FineCalc {
    
    private static final int gracePeriod = 15;
    private static final double finePerDay = 2.0;
    
    public static double calcFine(Book book, Date borrowedDate, Date returnedDate) {
        long diffInMs = returnedDate.getTime() - borrowedDate.getTime();
        long diffInDays = diffInMs / (1000 * 60 * 60 * 24);

        if (diffInDays <= gracePeriod) {
            return 0.0;
        }

        long extraDays = diffInDays - gracePeriod;
        double fine = 0.0;

        int chunks = (int) (extraDays / 10);
        long remaining = extraDays % 10;

        for (int i = 0; i < chunks; i++) {
            fine += finePerDay * 10 * Math.pow(2, i); 
        }

        fine += remaining * finePerDay * Math.pow(2, chunks);

        double maxFine = book.getCost() * 0.8;
        return Math.min(fine, maxFine);
    }

    public static double calculateLostBookFine(Book book) {
        return book.getCost() * 0.5;
    }

    public static double calculateCardLostFine() {
        return 10.0;
    }
}
