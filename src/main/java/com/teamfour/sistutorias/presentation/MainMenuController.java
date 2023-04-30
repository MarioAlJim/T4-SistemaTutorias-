package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.EducationProgram;
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
    private ComboBox<EducationProgram> cbEducationPrograms;
    @FXML
    private Tab tabTutor;
    @FXML
    private Tab tabCoordinator;
    @FXML
    private Tab tabJefe;

    private ObservableList<EducationProgram> educationPrograms = FXCollections.observableArrayList();
    private ArrayList<RoleProgram> availableRoles = SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getRolesPrograms();
    private RoleProgram roleProgramSelected = new RoleProgram();
    private  EducationProgram programSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabJefe.setDisable(true);
        tabTutor.setDisable(true);
        tabCoordinator.setDisable(true);
        getEducationPrograms();
    }

    private void setSessionGlobalDataRol(int role) {
        roleProgramSelected.setRole(role);
        roleProgramSelected.setEducationProgram(programSelected);
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    private void getEducationPrograms() {
        ArrayList<EducationProgram> educationPrograms = new ArrayList<>();
        for(RoleProgram roleProgram: availableRoles) {
            if (educationPrograms.size() == 0) {
                educationPrograms.add(roleProgram.getEducationProgram());
            } else {
                for (int i = 0; i < educationPrograms.size(); i++) {
                    if (educationPrograms.get(i).getIdEducationProgram() != roleProgram.getEducationProgram().getIdEducationProgram()) {
                        educationPrograms.add(roleProgram.getEducationProgram());
                    }
                }
            }
        }
        populateComboBox(educationPrograms);
    }

    private void populateComboBox(ArrayList<EducationProgram> educationProgram) {
        educationPrograms.addAll(educationProgram);
        cbEducationPrograms.setItems(educationPrograms);
        cbEducationPrograms.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            tabTutor.setDisable(true);
            tabCoordinator.setDisable(true);
            tabJefe.setDisable(true);
            programSelected = (EducationProgram) valorNuevo;
            for (RoleProgram roleProgram : availableRoles) {
                if (roleProgram.getEducationProgram().getName().equals(programSelected.getName())) {
                    switch (roleProgram.getRole()) {
                        case 1:
                            tabTutor.setDisable(false);
                            tabTutor.isSelected();
                            break;
                        case 2:
                            tabCoordinator.setDisable(false);
                            tabCoordinator.isSelected();
                            break;
                        case 3:
                            tabJefe.setDisable(false);
                            tabJefe.isSelected();
                            break;
                    }
                }
            }
        });
        cbEducationPrograms.getSelectionModel().selectFirst();
    }

    public void clickConsultTutorshipReport(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(1);

        /*WindowManagement.changeScene("Reportes generales de tutorías académicas",
                getClass().getResource("ConsultTutorshipReport.fxml"));*/
    }

    public void clickFillTutorshipReport(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Registrar reporte de tutoría académica",
                getClass().getResource("FillTutorshipReport.fxml"));
    }

    public void clickModifyProblem(ActionEvent event) throws IOException {
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
        WindowManagement.changeScene("Modificar problemática académica",
                getClass().getResource("ConsultAcademicProblems.fxml"));
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
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Asignación de tutor académico",
                getClass().getResource("TutorAssignment.fxml"));
    }

    public void clickModifyTutorAssignment(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Modificar asignación de tutor académico",
                getClass().getResource("ModifyAsignmentTutorTutorado.fxml"));
    }

    public void clickRegisterSolution(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(3);
        SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().setIdEducationProgram(1);
        WindowManagement.changeScene("Registrar solución a problemática académica",
                getClass().getResource("RegisterSolutionToAcademicProblem.fxml"));
    }

    public void clickConsultSolution(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(3);
        SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().setIdEducationProgram(1);
        WindowManagement.changeScene("Soluciones a problemáticas académicas",
                getClass().getResource("SolutionsToAcademicProblems.fxml"));
    }
}
