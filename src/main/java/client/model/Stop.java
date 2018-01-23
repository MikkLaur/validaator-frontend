package client.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Stop {
    private long id;
    private String name;
    private Date dateAdded;

    public Stop(long id, String name, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.dateAdded = dateAdded;
    }

    public Stop(String id, String name, String dateAdded) {
        this.id = Long.parseLong(id);
        this.name = name;
        this.dateAdded = Date.valueOf(dateAdded);
    }

    // TODO: Consolidate jsonArrayToList methods for Model classes
    public static List<Stop> jsonArrayToList(JSONArray jsonArray) {
        List<Stop> stops = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String stopId = json.keys().next();                     // Json key is the userId
            JSONObject personalInfo = json.getJSONObject(stopId);   // Get the subjson

            String name = personalInfo.getString("name");
            String dateAdded = personalInfo.getString("date_added");

            stops.add(new Stop(stopId,  name, dateAdded));
        }
        return stops;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
