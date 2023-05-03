package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class ModifiyTutorshipController implements Initializable
{
    @FXML
    private Button cancelButton;
    @FXML
    private Button addButton;
    @FXML
    private Label periodLabel;
    @FXML
    private Label firstSessionLabel;
    @FXML
    private Label endFirstSessionLabel;
    @FXML
    private Label instructionsLabel;
    @FXML
    private DatePicker firstDatePicker;
    @FXML
    private DatePicker endFirstDatePicker;
    @FXML
    private Label secondSessionLabel;
    @FXML
    private Label endSecondSessionLabel;
    @FXML
    private DatePicker secondDatePicker;
    @FXML
    private DatePicker endSecondDatePicker;
    @FXML
    private Label thirdSessionLabel;
    @FXML
    private Label endThirdSessionLabel;
    @FXML
    private DatePicker thirdDatePicker;
    @FXML
    private DatePicker endThirdDatePicker;
    @FXML
    private ComboBox cbPeriod;

    @FXML
    public void cancelAction(ActionEvent actionEvent) {
    }

    @FXML
    public void modifyAction(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}