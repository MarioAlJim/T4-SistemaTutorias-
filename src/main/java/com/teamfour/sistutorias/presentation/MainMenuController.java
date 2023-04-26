package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.EducationProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private ComboBox<EducationProgram> cbEducationPrograms;

    private ObservableList<EducationProgram> educationPrograms = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBox();
    }

    private void populateComboBox() {
        
    }

    public void clickConsultTutorshipReport(ActionEvent event) {
    }

    public void clickFillTutorshipReport(ActionEvent event) {
    }

    public void clickRegisterProblem(ActionEvent event) {
    }

    public void clickModifyProblem(ActionEvent event) {
    }

    public void clickRegisterTutorship(ActionEvent event) {
    }

    public void clickModifyTutorship(ActionEvent event) {
    }

    public void clickConsultGeneralTutorshipReport(ActionEvent event) {
    }

    public void clickConsultReportByTutor(ActionEvent event) {
    }

    public void clickManageTutorado(ActionEvent event) {
    }

    public void clickAssignTutor(ActionEvent event) {
    }

    public void clickModifyTutorAssignment(ActionEvent event) {
    }

    public void clickRegisterSolution(ActionEvent event) {
    }

    public void clickConsultSolution(ActionEvent event) {
    }
}
