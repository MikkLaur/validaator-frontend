package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ValidatorController {

    /* Kasutaja Loomine */
    public TextField userName;
    public TextField personal_id;
    public TextField date_of_birth;
    public Button registerUserButton;
    public TableView userHistoryTable;

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
    public ComboBox selectedUser;
    public ComboBox selectedStop;


    public void loadTransactions(ActionEvent actionEvent) {
    }

    private boolean basicDataValidation(TextField text) {
        return text.getText().length() != 0;
    }

    @FXML
    private void initialize() {
        HttpClient.getAllUsers();
    }
}
