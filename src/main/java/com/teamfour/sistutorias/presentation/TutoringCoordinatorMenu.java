package com.teamfour.sistutorias.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TutoringCoordinatorMenu implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickAssignTutor() throws IOException {
        WindowManagement.changeScene("Asignación de tutor",
                getClass().getResource("TutorAssignment.fxml"));
    }
}
