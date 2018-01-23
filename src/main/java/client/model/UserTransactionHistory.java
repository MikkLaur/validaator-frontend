package client.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//TODO: Rename userTransactionHistory
public class UserTransactionHistory {
    private long ticketNr;
    private String stopName;
    private Date transactionDate;

    public UserTransactionHistory(long ticketNr, String stopName, Date transactionDate) {
        this.ticketNr = ticketNr;
        this.stopName = stopName;
        this.transactionDate = transactionDate;
    }

    public UserTransactionHistory(String ticketNr, String stopName, String transactionDate) {
        this.ticketNr = Long.parseLong(ticketNr);
        this.stopName = stopName;
        this.transactionDate = Date.valueOf(transactionDate);
    }

    // TODO: Consolidate jsonArrayToList methods for Model classes
    public static List<UserTransactionHistory> jsonArrayToList(JSONArray jsonArray) {
        List<UserTransactionHistory> transactions = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String ticketNr = json.keys().next();                    // Json key is the ticketNr
            JSONObject personalInfo = json.getJSONObject(ticketNr);   // Get the subjson

            String stopName = personalInfo.getString("stop_name");
            String dateAdded = personalInfo.getString("date_added");

            transactions.add(new UserTransactionHistory(ticketNr, stopName, dateAdded));
        }
        return transactions;
    }

    public String getStringTicketNr() {
        return String.valueOf(ticketNr);
    }

    public long getTicketNr() {
        return ticketNr;
    }

    public void setTicketNr(long ticketNr) {
        this.ticketNr = ticketNr;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getFormattedDate() {
        /* formatting to be implemented */
        return transactionDate.toString();
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
