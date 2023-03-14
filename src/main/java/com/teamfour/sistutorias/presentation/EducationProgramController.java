package com.teamfour.sistutorias.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class EducationProgramController implements Initializable {

    //Temporal
    String educationPrograms[] = {"Ingenieria de software", "Estadistica"};
    @FXML
    private TextField tfEducationProgram;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickSave(ActionEvent event) {
        boolean isAlreadyRegistered = false;
        for(String educationProgram : educationPrograms) {
            if(educationProgram.equals(this.tfEducationProgram.getText())) {
                isAlreadyRegistered = true;
            }
        }

        if(isAlreadyRegistered) {
            WindowManagement.showAlert("Programa educativo no registrado",
                    "El programa educativo ha sido registrado anteriormente",
                    Alert.AlertType.WARNING);
        } else if (this.tfEducationProgram.getText().isEmpty() || this.tfEducationProgram.getText().isBlank()) {
            WindowManagement.showAlert("Programa educativo no registrado",
                    "No se ha ingresado un programa educativo",
                    Alert.AlertType.WARNING);
        } else {
            WindowManagement.showAlert("Programa educativo registrado exitosamente",
                    "El programa educativo ha sido registrado exitosamente",
                    Alert.AlertType.CONFIRMATION);
        }
    }
}
