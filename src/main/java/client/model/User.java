package client.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User {
    private long id;
    private String name;
    private String personalId;
    private Date dateOfBirth;
    private Date dateAdded;

    public User(long id, String name, String personalId, Date dateOfBirth, Date dateAdded) {
        this.id = id;
        this.name = name;
        this.personalId = personalId;
        this.dateOfBirth = dateOfBirth;
        this.dateAdded = dateAdded;
    }

    public User(String id, String name, String personalId, String dateOfBirth, String dateAdded) {
        this.id = Long.parseLong(id);
        this.name = name;
        this.personalId = personalId;
        this.dateOfBirth = Date.valueOf(dateOfBirth);
        this.dateAdded = Date.valueOf(dateAdded);
    }

    // TODO: Create a Controller for this Model and move this method out
    public static List<User> jsonArrayToList(JSONArray jsonArray) {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            String userId = json.keys().next();                     // Json key is the userId
            JSONObject personalInfo = json.getJSONObject(userId);   // Get the subjson

            String name = personalInfo.getString("name");
            String personalId = personalInfo.getString("personal_id");
            String dateOfBirth = personalInfo.getString("date_of_birth");
            String dateAdded = personalInfo.getString("date_added");

            users.add(new User(userId,  name, personalId, dateOfBirth, dateAdded));
        }
        return users;
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

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }
}
