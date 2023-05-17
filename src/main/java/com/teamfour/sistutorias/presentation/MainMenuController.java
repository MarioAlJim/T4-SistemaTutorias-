package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.domain.EducationProgram;
import com.teamfour.sistutorias.domain.RoleProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {
    @FXML
    private ComboBox<EducationProgram> cbEducationPrograms;
    @FXML
    private TabPane tpRoles;
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
        educationPrograms.add(availableRoles.get(0).getEducationProgram());
        for(RoleProgram roleProgram: availableRoles) {
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
        populateComboBox(educationPrograms);
    }

    private void populateComboBox(ArrayList<EducationProgram> educationProgram) {
        educationPrograms.addAll(educationProgram);
        cbEducationPrograms.setItems(educationPrograms);
        cbEducationPrograms.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            tabTutor.setDisable(true);
            tabCoordinator.setDisable(true);
            tabJefe.setDisable(true);
            programSelected = (EducationProgram) valorNuevo;;
            for (RoleProgram roleProgram : availableRoles) {
                if (roleProgram.getEducationProgram().getName().equals(programSelected.getName())) {
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
            if(!tabTutor.isDisable()) {
                tpRoles.getSelectionModel().select(tabTutor);
            } else if(!tabCoordinator.isDisable()) {
                tpRoles.getSelectionModel().select(tabCoordinator);
            } else if(!tabJefe.isDisable()) {
                tpRoles.getSelectionModel().select(tabJefe);
            }
        });
        cbEducationPrograms.getSelectionModel().selectFirst();
    }

    public void clickConsultTutorshipReport() throws IOException {
        setSessionGlobalDataRol(1);
        WindowManagement.changeScene("Reporte de tutoria academica",
                getClass().getResource("ConsultReport.fxml"));
    }

    public void clickFillTutorshipReport() throws IOException {
        setSessionGlobalDataRol(1);
        if (activeTutorship()) {
            WindowManagement.changeScene("Registrar reporte de tutoría académica",
                    getClass().getResource("FillTutorshipReport.fxml"));
        }
    }

    public void clickConsultAcademicProblem() throws IOException {
        setSessionGlobalDataRol(1);
        WindowManagement.changeScene("Problemáticas académicas",
                getClass().getResource("ConsultAcademicProblems.fxml"));
    }

    public void clickManageTutorship() throws IOException {
        setSessionGlobalDataRol(2);
        if (activePeriod()) {
            WindowManagement.changeScene("Registrar sesión de tutoría académica",
                    getClass().getResource("Tutorship.fxml"));
        }
    }

    public void clickConsultGeneralTutorshipReport() throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Reporte General de Tutoría Académica",
                getClass().getResource("ConsultGeneralTutorshipReport.fxml"));
    }

    public void clickConsultReportByTutor() throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Reporte de tutoría",
                getClass().getResource("ConsultTutorshipReport.fxml"));
    }

    public void clickManageTutorado() throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Administrar tutorados",
                getClass().getResource("ManageTutorados.fxml"));
    }

    public void clickAssignTutor() throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Asignación de tutor académico",
                getClass().getResource("TutorAssignment.fxml"));
    }

    public void clickModifyTutorAssignment() throws IOException {
        setSessionGlobalDataRol(2);
        WindowManagement.changeScene("Modificar asignación de tutor académico",
                getClass().getResource("ModifyAsignmentTutorTutorado.fxml"));
    }

    public void clickRegisterSolution() throws IOException {
        setSessionGlobalDataRol(3);
        WindowManagement.changeScene("Registrar solución a problemática académica",
                getClass().getResource("RegisterSolutionToAcademicProblem.fxml"));
    }

    public void clickConsultSolution() throws IOException {
        setSessionGlobalDataRol(3);
        WindowManagement.changeScene("Soluciones a problemáticas académicas",
                getClass().getResource("SolutionsToAcademicProblems.fxml"));
    }

    private boolean activeTutorship() {
        if (SessionGlobalData.getSessionGlobalData().getCurrentTutorship().getIdTutorShip() != 0) {
            return true;
        } else {
            WindowManagement.showAlert("No disponible", "La accion no se puede realizar debido a que no hay tutorias activas", Alert.AlertType.INFORMATION);
            return false;
        }
    }

    private boolean activePeriod () {
        if (SessionGlobalData.getSessionGlobalData().getCurrentPeriod().getIdPeriod() != 0) {
            return true;
        } else {
            WindowManagement.showAlert("No disponible", "La accion no se puede realizar debido a que no hay periodos activos", Alert.AlertType.INFORMATION);
            return false;
        }
    }

    public void clickAcademicOffer(ActionEvent actionEvent) throws IOException {
        setSessionGlobalDataRol(1);
        WindowManagement.changeScene("Oferta académica",
                getClass().getResource("GroupQuery.fxml"));
    }
}
