package com.teamfour.sistutorias.presentation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CareerManagerMenu implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickRegisterSolution() throws IOException {
        WindowManagement.changeScene("Problemáticas académicas sin solución",
                getClass().getResource("AcademicProblemsWithoutSolution.fxml"));
    }

    @FXML
    private void clickConsultSolutions() throws IOException {
        WindowManagement.changeScene("Soluciones a problemáticas académicas",
                getClass().getResource("SolutionsToAcademicProblems.fxml"));
    }
}
