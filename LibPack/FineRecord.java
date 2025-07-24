package LibPack;
import java.util.*;

public class FineRecord {
    private String reason;
    private double amount;
    private Date date;

    public FineRecord(String reason, double amount, Date date){
        this.reason = reason;
        this.amount = amount;
        this.date = date;
    }

    public String getReason(){
        return reason;
    }

    public double getAmount(){
        return amount;
    }

    public Date getDate(){
        return date;
    }

    public String getAsRow(){
        return (Utils.formatDate(date)+"   "+amount+"   "+reason);
    }
}
