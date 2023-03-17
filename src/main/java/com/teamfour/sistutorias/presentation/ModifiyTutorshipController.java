package com.teamfour.sistutorias.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifiyTutorshipController implements Initializable {
    @javafx.fxml.FXML
    private Button cancelButton;
    @javafx.fxml.FXML
    private Button addButton;
    @javafx.fxml.FXML
    private Label periodLabel;
    @javafx.fxml.FXML
    private Label firstSessionLabel;
    @javafx.fxml.FXML
    private Label endFirstSessionLabel;
    @javafx.fxml.FXML
    private Label instructionsLabel;
    @javafx.fxml.FXML
    private DatePicker firstDatePicker;
    @javafx.fxml.FXML
    private DatePicker endFirstDatePicker;
    @javafx.fxml.FXML
    private Label secondSessionLabel;
    @javafx.fxml.FXML
    private Label endSecondSessionLabel;
    @javafx.fxml.FXML
    private DatePicker secondDatePicker;
    @javafx.fxml.FXML
    private DatePicker endSecondDatePicker;
    @javafx.fxml.FXML
    private Label thirdSessionLabel;
    @javafx.fxml.FXML
    private Label endThirdSessionLabel;
    @javafx.fxml.FXML
    private DatePicker thirdDatePicker;
    @javafx.fxml.FXML
    private DatePicker endThirdDatePicker;
    @javafx.fxml.FXML
    private ComboBox cbPeriod;
    public void cancelAction(ActionEvent actionEvent) {
        WindowManagement.closeWindow(actionEvent);
    }

    public void modifyAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
