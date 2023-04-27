package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.RoleProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private ComboBox<RoleProgram> cbEducationPrograms;
    @FXML
    private Tab tabTutor;
    @FXML
    private Tab tabCoordinator;
    @FXML
    private Tab tabJefe;

    private ObservableList<RoleProgram> educationPrograms = FXCollections.observableArrayList();

    private RoleProgram roleProgramSelected;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        populateComboBox();
        tabJefe.setDisable(true);
        tabTutor.setDisable(true);
        tabCoordinator.setDisable(true);
    }

    private void populateComboBox() {
        ArrayList<RoleProgram> availableRoles = SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getRolesPrograms();
        educationPrograms.addAll(availableRoles);
        cbEducationPrograms.setItems(educationPrograms);
        cbEducationPrograms.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            roleProgramSelected = (RoleProgram) valorNuevo;
            switch (roleProgramSelected.getIdRoleProgram()) {
                case 1:
                    tabJefe.setDisable(true);
                    tabTutor.setDisable(false);
                    tabCoordinator.setDisable(true);
                    break;
                case 2:
                    tabJefe.setDisable(true);
                    tabTutor.setDisable(true);
                    tabCoordinator.setDisable(false);
                    break;
                case 3:
                    tabJefe.setDisable(false);
                    tabTutor.setDisable(true);
                    tabCoordinator.setDisable(true);
                    break;
            }
        });
    }

    public void clickConsultTutorshipReport(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickFillTutorshipReport(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickRegisterProblem(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickModifyProblem(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickRegisterTutorship(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickModifyTutorship(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickConsultGeneralTutorshipReport(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickConsultReportByTutor(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickManageTutorado(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickAssignTutor(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Asignación de tutor académico",
                getClass().getResource("TutorAssignment.fxml"));
    }

    public void clickModifyTutorAssignment(ActionEvent event) {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    public void clickRegisterSolution(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Registrar solución a problemática académica",
                getClass().getResource("RegisterSolutionToAcademicProblem.fxml"));
    }

    public void clickConsultSolution(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Soluciones a problemáticas académicas",
                getClass().getResource("SolutionsToAcademicProblems.fxml"));
    }
}
