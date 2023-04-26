package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EEDAO;
import com.teamfour.sistutorias.bussinesslogic.EducationProgramDAO;
import com.teamfour.sistutorias.bussinesslogic.GroupDAO;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.EE;
import com.teamfour.sistutorias.domain.EducationProgram;
import com.teamfour.sistutorias.domain.Group;
import com.teamfour.sistutorias.domain.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GroupAdministrationController implements Initializable {
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveGroup;
    @FXML
    private TextField tfNRC;
    @FXML
    private TableView<Group> tvGroup;
    @FXML
    private TableColumn tcPersonalNum;
    @FXML
    private TableColumn tcName;
    @FXML
    private Button btnDeleteGroup;
    @FXML
    private Button btnModifyGroup;
    @FXML
    private Label lbl_action;
    @FXML
    private Button btnCancelModification;
    @FXML
    private TableView<Teacher> tvTeacher;
    @FXML
    private TableColumn tcNameTeacher;
    @FXML
    private TableView<EE> tvEE;
    @FXML
    private TableColumn tcEE;
    @FXML
    private TextField tfSelectedEE;
    @FXML
    private TextField tfSelectedTeacher;
    @FXML
    private ComboBox cbbAcademicProblem;
    private EducationProgram selectedEducationProgram;
    private Teacher selectedTeacher;
    private EE selectedEe;
    @FXML
    private TableColumn tcNrc;
    @FXML
    private TableColumn tcEeG;

    private void lockModify(boolean lock) {
        if (lock) {
            btnCancelModification.setDisable(true);
            btnModifyGroup.setDisable(true);
            btnDeleteGroup.setDisable(true);
            btnSaveGroup.setDisable(false);
        } else {
            btnCancelModification.setDisable(false);
            btnModifyGroup.setDisable(false);
            btnDeleteGroup.setDisable(false);
            btnSaveGroup.setDisable(true);
        }

    }

    private void setEducationProgram() {
        EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
        ArrayList<EducationProgram> educationPrograms;
        ObservableList<EducationProgram> educationProgramsList = FXCollections.observableArrayList();
        try {
            educationPrograms = educationProgramDAO.getEducationPrograms();
            educationProgramsList.addAll(educationPrograms);
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupAdministrationController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        cbbAcademicProblem.setItems(educationProgramsList);
        cbbAcademicProblem.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            selectedEducationProgram = (EducationProgram) valorNuevo;
            setTeachers();
            setEes();
            setGroups();
        });
    }

    private void setTeachers() {
        tcNameTeacher.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tcPersonalNum.setCellValueFactory(new PropertyValueFactory<>("personalNumber"));
        TeacherDAO teacherDAO = new TeacherDAO();
        ArrayList<Teacher> teachers;
        ObservableList<Teacher> teachersList = FXCollections.observableArrayList();
        try {
            teachers = teacherDAO.getAllTeachers();
            teachersList.addAll(teachers);
        } catch (SQLException ex) {
            WindowManagement.showAlert("Error", "Error al conectar con la base de datos", Alert.AlertType.INFORMATION);
        }
        tvTeacher.setItems(teachersList);
    }

    private void setEes() {
        tcEE.setCellValueFactory(new PropertyValueFactory<>("name"));
        EEDAO eedao = new EEDAO();
        ArrayList<EE> ees;
        ObservableList<EE> eesList = FXCollections.observableArrayList();
        try {
            ees = eedao.getAllEe();
            eesList.addAll(ees);
        } catch (SQLException sqlException) {
            WindowManagement.showAlert("Error", "Error al conectar con la base de datos", Alert.AlertType.INFORMATION);
        }
        tvEE.setItems(eesList);
    }

    private void setGroups() {
        tcEeG.setCellValueFactory(new PropertyValueFactory<>("name"));
        ArrayList<Group> groups;
        ObservableList<Group> groupsList = FXCollections.observableArrayList();
        try {
            GroupDAO groupDAO = new GroupDAO();
            groups = groupDAO.groupsList(selectedEducationProgram.getIdEducationProgram());
            groupsList.addAll(groups);
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        tvGroup.setItems(groupsList);
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {

    }

    @FXML
    public void cancelModification(ActionEvent actionEvent) {
    }

    @FXML
    public void saveGroups(ActionEvent actionEvent) {
    }

    @FXML
    public void deleteGroup(ActionEvent actionEvent) {
    }

    @FXML
    public void modifyGroup(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setEducationProgram();
        lockModify(true);
        btnSaveGroup.setDisable(true);
    }
}
