package com.teamfour.sistutorias.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProgramaEducativoController implements Initializable {
    @FXML
    private TextField tfProgramaEducativo;

    //Temporal
    String educationProgram[] = {"Ingenieria de software", "Estadistica"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickSave(ActionEvent event) {

    }
}
