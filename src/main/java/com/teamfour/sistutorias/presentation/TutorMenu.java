package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TutorMenu implements Initializable {
    @FXML
    private Button btnConsultAcademicProblems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickConsultAcademicProblems() {
        try {
            WindowManagement.changeScene("Consultar problematicas academicas",
                    getClass().getResource("ConsultAcademicProblems.fxml"));
        } catch (IOException exception) {
            Logger.getLogger(IOException.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}
