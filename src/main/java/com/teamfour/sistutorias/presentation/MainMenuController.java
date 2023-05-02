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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private ArrayList<RoleProgram> availableRoles;
    private RoleProgram roleProgramSelected = new RoleProgram();
    private  EducationProgram programSelected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabJefe.setDisable(true);
        tabTutor.setDisable(true);
        tabCoordinator.setDisable(true);
        getEducationPrograms();


        URL linkImgTutor = getClass().getResource("/com/teamfour/sistutorias/images/luzio_tutor.jpg");
        Image imgTutor = new Image(linkImgTutor.toString(),20,20,false,true);
        tabTutor.setGraphic(new ImageView(imgTutor));
        URL linkImgcoordinator = getClass().getResource("/com/teamfour/sistutorias/images/luzio_coordinador.png");
        Image imgCoordinator = new Image(linkImgcoordinator.toString(),20,20,false,true);
        tabCoordinator.setGraphic(new ImageView(imgCoordinator));
        URL linkImgHead = getClass().getResource("/com/teamfour/sistutorias/images/luzio_jefe.jpg");
        Image imgHead = new Image(linkImgHead.toString(),20,20,false,true);
        tabJefe.setGraphic(new ImageView(imgHead));
    }

    private void setSessionGlobalDataRol(int role) {
        roleProgramSelected.setRole(role);
        roleProgramSelected.setEducationProgram(programSelected);
        SessionGlobalData.getSessionGlobalData().setActiveRole(roleProgramSelected);
    }

    private void getEducationPrograms() {
        availableRoles = SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getRolesPrograms();
        ArrayList<EducationProgram> educationPrograms = new ArrayList<>();
        for(RoleProgram roleProgram: availableRoles) {
            if (educationPrograms.isEmpty()) {
                educationPrograms.add(roleProgram.getEducationProgram());
            } else {
                boolean isInCombobox = false;
                for (int i = 0; i < educationPrograms.size(); i++) {
                    if (educationPrograms.get(i).getIdEducationProgram() == roleProgram.getEducationProgram().getIdEducationProgram()) {
                        isInCombobox = true;
                    }
                    if(!isInCombobox) {
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
        setSessionGlobalDataRol(1);
        WindowManagement.changeScene("Registrar reporte de tutoría académica",
                getClass().getResource("FillTutorshipReport.fxml"));
    }

    public void clickConsultAcademicProblem(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(1);
        WindowManagement.changeScene("Problemáticas académicas",
                getClass().getResource("ConsultAcademicProblems.fxml"));
    }

    public void clickRegisterTutorship(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Registrar sesión de tutoría académica",
                getClass().getResource("Tutorship.fxml"));
    }

    public void clickModifyTutorship(ActionEvent event) throws IOException {
        //TODO
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Modificar sesión de tutoría académica",
                getClass().getResource(".fxml"));
    }

    public void clickConsultGeneralTutorshipReport(ActionEvent event) throws IOException {
        //TODO ???
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Reporte General de Tutoría Académica",
                getClass().getResource("ConsultGeneralTutorshipReport.fxml"));
    }

    public void clickConsultReportByTutor(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Reporte de tutoría",
                getClass().getResource("ConsultTutorshipReport.fxml"));
    }

    public void clickManageTutorado(ActionEvent event) throws IOException {
        //TODO
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("",
                getClass().getResource(""));
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
        WindowManagement.changeScene("Registrar solución a problemática académica",
                getClass().getResource("RegisterSolutionToAcademicProblem.fxml"));
    }

    public void clickConsultSolution(ActionEvent event) throws IOException {
        setSessionGlobalDataRol(3);
        WindowManagement.changeScene("Soluciones a problemáticas académicas",
                getClass().getResource("SolutionsToAcademicProblems.fxml"));
    }
}
