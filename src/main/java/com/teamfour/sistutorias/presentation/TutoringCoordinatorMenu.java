package com.teamfour.sistutorias.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;

public class TutoringCoordinatorMenu implements Initializable {
    @FXML
    private Button btnModifyTutor;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickAssignTutor() throws IOException {
        WindowManagement.changeScene("Asignación de tutor",
                getClass().getResource("TutorAssignment.fxml"));
    }

    @FXML
    public void clickModifyTutor(ActionEvent actionEvent) {
        try {
            WindowManagement.changeScene("Modificar asignacion de tutor",
                    getClass().getResource("ModifyAsignmentTutorTutorado.fxml"));
        } catch (IOException exception) {
            Logger.getLogger(IOException.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}
