package client;

import client.model.Stop;
import client.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;

public class ValidatorController {
    private List<User> usersList;
    private List<Stop> stopsList;

    /* TableView */
    public TableView userHistoryTable;
    public TableColumn ticketNrColumn;
    public TableColumn stopNameColumn;
    public TableColumn ticketPurchaseDateColumn;


    /* Kasutaja Loomine */
    public TextField userName;
    public TextField personal_id;
    public TextField date_of_birth;
    public Button registerUserButton;

    public void registerUserClicked(ActionEvent actionEvent) {
        if (basicDataValidation(userName) && basicDataValidation(personal_id) && basicDataValidation(date_of_birth)) {
            HttpClient.registerUser(userName.getText(), personal_id.getText(), date_of_birth.getText());
        }
    }

    /* Peatuse Loomine */
    public TextField stopName;
    public Button registerStopButton;

    public void registerStopClicked(ActionEvent actionEvent) {
        if (basicDataValidation(stopName)) {
            HttpClient.registerStop(stopName.getText());
        }
    }


    /* ValidatorController */
    public ComboBox usersComboBox;
    public ComboBox stopsComboBox;


    public void loadTransactions(ActionEvent actionEvent) {
    }



    /* Other stuff */
    private boolean basicDataValidation(TextField text) {
        return text.getText().length() != 0;
    }

    private void populateStopSelectionComboBox(List<Stop> stops) {
        ObservableList stopNames = FXCollections.observableArrayList(stops.stream()
                .map(Stop::getName)
                .collect(Collectors.toList()));
        stopsComboBox.setItems(stopNames);
    }

    private void populateUserSelectionComboBox(List<User> users) {
        ObservableList userIds = FXCollections.observableArrayList(users.stream()
                .map(User::getId)
                .collect(Collectors.toList()));
        usersComboBox.setItems(userIds);
    }

      //////////////////
     /* Constructors */
    //////////////////

    public ValidatorController() {
        /* Query all the existing users from the Database */
        JSONArray jsonArray = new JSONArray(HttpClient.getAllUsers());
        usersList = User.jsonArrayToList(jsonArray);
        /* Query all existing stops from the Database */
        jsonArray = new JSONArray(HttpClient.getAllStops());
        stopsList = Stop.jsonArrayToList(jsonArray);
    }

    @FXML
    private void initialize() {

        populateUserSelectionComboBox(usersList);
        populateStopSelectionComboBox(stopsList);

    }
}
