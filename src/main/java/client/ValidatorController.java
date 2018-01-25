package client;

import client.model.Stop;
import client.model.User;
import client.model.UserTransactionHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

//TODO: Set this piece of crap on fire (aka Separate the code into multiple classes)
public class ValidatorController {
    /* Containers for users and stops */
    private List<User> usersList;
    private List<Stop> stopsList;

    /* User creation */
    public TextField userName;
    public TextField personal_id;
    public TextField date_of_birth;
    public Button registerUserButton;

    /* Stop creation */
    public TextField stopName;
    public Button registerStopButton;

    /* TableView and its TableColumn declarations */
    public TableView<UserTransactionHistory> userHistoryTable;
    public TableColumn<UserTransactionHistory, Long> ticketNrColumn;
    public TableColumn<UserTransactionHistory, String> stopNameColumn;
    public TableColumn<UserTransactionHistory, Date> ticketPurchaseDateColumn;

    /* ComboBox declarations */
    public ComboBox<Long> usersComboBox;
    public ComboBox<String> stopsComboBox;
    /* Stops drop-down menu manipulation (ComboBox)*/
    private ObservableList<String> stopNames;
    /* Users drop-down menu manipulation (ComboBox)*/
    private ObservableList<Long> userIds;

    /* User registration method */
    public void registerUserClicked(ActionEvent actionEvent) {
        if (basicDataValidation(userName) && basicDataValidation(personal_id) && basicDataValidation(date_of_birth)) {
            long userId = HttpClient.registerUser(userName.getText(), personal_id.getText(), date_of_birth.getText());
            if (userId != -1 ) addNewUserId(userId); /* If the registration failed, the server will respond with -1 */
        }
    }
    /* Stop registration method */
    public void registerStopClicked(ActionEvent actionEvent) {
        if (basicDataValidation(stopName)) {
            long stopId = HttpClient.registerStop(stopName.getText());
            addNewStopName(new Stop(stopId, stopName.getText()));
        }
    }

    /* Validating a ticket */
    //TODO: Move logic out of view
    public void validateClicked(ActionEvent actionEvent) {
        Long selectedUserId = (Long) usersComboBox.getValue();
        String selectedStopName = (String) stopsComboBox.getValue();
        if (selectedUserId != null && selectedStopName != null) {
            /* Get stopId before making a http request */
            Long selectedStopId = stopsList.stream()
                    .filter(c -> c.getName().equals(selectedStopName))
                    .findFirst().orElse(null)
                    .getId();
            //System.out.println(selectedUserId + " " + stopId);
            HttpClient.sendValidation(selectedUserId.toString(), selectedStopId.toString());

            /* Reload transaction table */
            //TODO: update locally instead of querying the server again
            loadTransactions(new ActionEvent());
        }
        //System.out.println(selectedUserId + selectedStopName);
    }


    /* Loads the transaction history for the currently selected user and populates the TableView */
    //TODO: Move logic out of view
    public void loadTransactions(ActionEvent actionEvent) {
        if (usersComboBox.getValue() != null) {
            Long currentUserId = usersComboBox.getValue();
            JSONArray jsonArray = new JSONArray(HttpClient.getUserTransactionHistory(currentUserId.toString()));
            List<UserTransactionHistory> userTransactionHistory = UserTransactionHistory.jsonArrayToList(jsonArray);

            userHistoryTable.setItems(FXCollections.observableArrayList(userTransactionHistory));
        }
    }


    /* Loads stop names to the stops drop down menu */
    private void populateStopSelectionComboBox(List<Stop> stops) {
        stopNames = FXCollections.observableArrayList(stops.stream()
                .map(Stop::getName)
                .collect(Collectors.toList()));
        stopsComboBox.setItems(stopNames);
    }
    /* Appends a stop name to the drop down menu */
    private void addNewStopName(Stop stop) {
        stopsList.add(stop);
        stopNames.add(stop.getName());
    }

    /* Loads user names to the users drop down menu */
    private void populateUserSelectionComboBox(List<User> users) {
        userIds = FXCollections.observableArrayList(users.stream()
                .map(User::getId)
                .collect(Collectors.toList()));
        usersComboBox.setItems(userIds);
    }
    /* Appends a user id to the users drop down menu */
    private void addNewUserId(long id) {
        userIds.add(id);
    }

    /* Data validation methods */
    private boolean basicDataValidation(TextField text) {
        return text.getText().length() != 0;
    }

     /* Constructors */
    public ValidatorController() {
        //TODO: Check whether the server is up.
        /* Query all the existing users from the Database */
        JSONArray jsonArray = new JSONArray(HttpClient.getAllUsers());
        usersList = User.jsonArrayToList(jsonArray);
        /* Query all existing stops from the Database */
        jsonArray = new JSONArray(HttpClient.getAllStops());
        stopsList = Stop.jsonArrayToList(jsonArray);
    }

    @FXML
    private void initialize() {
        /* Assign properties to TableView Columns */
        ticketNrColumn.setCellValueFactory(
                new PropertyValueFactory<>("ticketNr")
        );
        stopNameColumn.setCellValueFactory(
                new PropertyValueFactory<>("stopName")
        );
        ticketPurchaseDateColumn.setCellValueFactory(
                new PropertyValueFactory<>("transactionDate")
        );

        populateUserSelectionComboBox(usersList);
        populateStopSelectionComboBox(stopsList);
    }
}
