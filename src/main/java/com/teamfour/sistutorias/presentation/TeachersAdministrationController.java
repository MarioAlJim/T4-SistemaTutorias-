package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.domain.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TeachersAdministrationController implements Initializable {
    @FXML
    private TextField tfNumberPersonal;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfPaternalSurname;
    @FXML
    private TextField tfMaternalSurname;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSaveTeacher;
    @FXML
    private TableView<Teacher> tvTeacher;
    @FXML
    private TableColumn tcPersonalNum;
    @FXML
    private TableColumn tcName;
    @FXML
    private Label lbl_action;
    @FXML
    private Button btnModifyTeacher;

    private ObservableList<Teacher> teachersList;
    private Teacher newTeacher = new Teacher();

    private void setTeachers() {
        tcName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tcPersonalNum.setCellValueFactory(new PropertyValueFactory<>("personalNumber"));
        TeacherDAO teacherDAO = new TeacherDAO();
        ArrayList<Teacher> teachers = new ArrayList<>();
        this.teachersList = FXCollections.observableArrayList();
        try {
            teachers = teacherDAO.getAllTeachers();
            teachersList.addAll(teachers);
        } catch (SQLException ex) {
            WindowManagement.showAlert("Error", "Error al conectar con la base de datos", Alert.AlertType.INFORMATION);
        }
        tvTeacher.setItems(teachersList);
    }
    private int validateData() {
        int validData = 0;
        if(DataValidation.textValidation(tfName.getText(), 30)) {
            validData++;
            newTeacher.setName(tfName.getText());
        }
        if(DataValidation.textValidation(tfMaternalSurname.getText(), 30)) {
            validData++;
            newTeacher.setMaternalSurname(tfMaternalSurname.getText());
        }
        if(DataValidation.textValidation(tfPaternalSurname.getText(), 30)){
           validData++;
           newTeacher.setPaternalSurname(tfPaternalSurname.getText());
        }
        if(DataValidation.numberValidation(tfNumberPersonal.getText()) != -1) {
            newTeacher.setPersonalNumber(DataValidation.numberValidation(tfNumberPersonal.getText()));
            validData++;
        }
        return validData;
    }

    private void newSave() {
        int result = 0;
        TeacherDAO teacherDAO = new TeacherDAO();
        try {
            result = teacherDAO.registerTeacher(newTeacher);
            if(result == 1) {
                WindowManagement.showAlert("Exito", "Docente registrado", Alert.AlertType.INFORMATION);
            } else {
                WindowManagement.showAlert("Error", "El docente que intenta registrar ya se encuentra en el sistema", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            WindowManagement.showAlert("Error", "Error al conectar con la base de datos", Alert.AlertType.INFORMATION);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTeachers();
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveTeacher(ActionEvent actionEvent) {
        if (tfNumberPersonal.getText().isEmpty() || tfName.getText().isEmpty() || tfPaternalSurname.getText().isEmpty() || tfMaternalSurname.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
        } else {
            if (validateData() == 4) {
                newSave();
            } else {
                WindowManagement.showAlert("Error", "Caracteres invalidos detectados, verifique", Alert.AlertType.INFORMATION);
            }
        }
    }


    @FXML
    public void modifyTeacher(ActionEvent actionEvent) {

    }

    public void deleteTeacher(ActionEvent actionEvent) {

    }

    public void newModification(ActionEvent actionEvent) {

    }
}
