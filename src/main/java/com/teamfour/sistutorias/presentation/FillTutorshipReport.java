package com.teamfour.sistutorias.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class FillTutorshipReport {

    @FXML
    private Label lbAssistantPercentage;

    @FXML
    private Label lbDate;

    @FXML
    private Label lbPeriod;

    @FXML
    private Label lbRiskPercentage;

    @FXML
    private TableColumn<?, ?> tcAssistance;

    @FXML
    private TableColumn<?, ?> tcRisk;

    @FXML
    private TableColumn<?, ?> tcTutored;

    @FXML
    private TableView<?> tvTutored;

    @FXML
    void closeWindow(ActionEvent event) {

    }

    @FXML
    void openAddComment(ActionEvent event) {

    }

    @FXML
    void openRegisterProblem(ActionEvent event) {

    }

    @FXML
    void saveRegister(ActionEvent event) {

    }

}
