package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EducationProgramDAO;
import com.teamfour.sistutorias.domain.EducationProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EducationProgramController implements Initializable {
    @FXML
    private TextField tfEducationProgram;

    private ArrayList<EducationProgram> registeredEducationPrograms = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickSave(ActionEvent event) {
        if (this.tfEducationProgram.getText().isEmpty() || this.tfEducationProgram.getText().isBlank()) {
            WindowManagement.showAlert("Programa educativo no registrado",
                    "No se ha ingresado un programa educativo",
                    Alert.AlertType.WARNING);
        } else {
            boolean isAlreadyRegistered = false;
            EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
            try {
                registeredEducationPrograms = educationProgramDAO.getEducationPrograms();
                for(EducationProgram educationProgram : registeredEducationPrograms) {
                    if(educationProgram.getName().equals(this.tfEducationProgram.getText())) {
                        isAlreadyRegistered = true;
                    }
                }

                if(isAlreadyRegistered) {
                    WindowManagement.showAlert("Programa educativo no registrado",
                            "El programa educativo ha sido registrado anteriormente",
                            Alert.AlertType.WARNING);
                } else {
                    EducationProgram educationProgram = new EducationProgram();
                    educationProgram.setName(this.tfEducationProgram.getText());
                    boolean educationProgramWasRegistered = educationProgramDAO.register(educationProgram);
                    if(educationProgramWasRegistered) {
                        this.tfEducationProgram.clear();
                        WindowManagement.showAlert("Programa educativo registrado exitosamente",
                                "El programa educativo ha sido registrado exitosamente",
                                Alert.AlertType.CONFIRMATION);
                    } else {
                        WindowManagement.showAlert("Programa educativo no registrado",
                                "El programa educativo no se ha registrado",
                                Alert.AlertType.ERROR);
                    }
                }
            } catch (SQLException sqlException) {
                WindowManagement.connectionLostMessage();
            }
        }
    }
}
