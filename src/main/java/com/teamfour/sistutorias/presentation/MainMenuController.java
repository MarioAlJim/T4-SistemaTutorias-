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
    private ComboBox<String> cbEducationPrograms;
    @FXML
    private Tab tabTutor;
    @FXML
    private Tab tabCoordinator;
    @FXML
    private Tab tabJefe;

    private ObservableList<String> educationPrograms = FXCollections.observableArrayList();
    private ArrayList<RoleProgram> availableRoles = SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getRolesPrograms();
    private RoleProgram roleProgramSelected;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabJefe.setDisable(true);
        tabTutor.setDisable(true);
        tabCoordinator.setDisable(true);

        getEducationPrograms();
    }

    private void getEducationPrograms() {
        ArrayList<String> educationPrograms = new ArrayList<>();
        for(RoleProgram roleProgram: availableRoles) {
            System.out.println(roleProgram.getRole() + " " + roleProgram.getNameProgram());
            if(!educationPrograms.contains(roleProgram.getNameProgram())) {
                educationPrograms.add(roleProgram.getNameProgram());
            }
        }
        populateComboBox(educationPrograms);
    }

    private void populateComboBox(ArrayList<String> educationProgram) {
        educationPrograms.addAll(educationProgram);
        cbEducationPrograms.setItems(educationPrograms);
        cbEducationPrograms.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            tabTutor.setDisable(true);
            tabCoordinator.setDisable(true);
            tabJefe.setDisable(true);
            String programSelected = (String) valorNuevo;
            for (RoleProgram roleProgram : availableRoles) {
                if (roleProgram.getNameProgram().equals(programSelected)) {
                    switch (roleProgram.getRole()) {
                        case 1:
                            tabTutor.setDisable(false);
                            break;
                        case 2:
                            tabCoordinator.setDisable(false);
                            break;
                        case 3:
                            tabJefe.setDisable(false);
                            break;
                    }
                }
            }
        });
    }

    public void clickConsultTutorshipReport(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Reportes generales de tutorías académicas",
                getClass().getResource(".fxml"));
    }

    public void clickFillTutorshipReport(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Registrar reporte de tutoría académica",
                getClass().getResource("FillTutorshipReport.fxml"));
    }

    public void clickRegisterProblem(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Registrar problemática académica",
                getClass().getResource(".fxml"));
    }

    public void clickModifyProblem(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Modificar problemática académica",
                getClass().getResource(".fxml"));
    }

    public void clickRegisterTutorship(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Registrar sesión de tutoría académica",
                getClass().getResource(".fxml"));
    }

    public void clickModifyTutorship(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Modificar sesión de tutoría académica",
                getClass().getResource(".fxml"));
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

    public void clickModifyTutorAssignment(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Modificar asignación de tutor académico",
                getClass().getResource("ModifyAsignmentTutorTutorado.fxml"));
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
