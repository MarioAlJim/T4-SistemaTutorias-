package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
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
    private Button btnModify;
    @FXML
    private Button btnCancelModification;
    @FXML
    private Button btnDelete;
    private ObservableList<Teacher> teachersList;
    private Teacher newTeacher = new Teacher();
    private int oldPersonalNumber;

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

    private void seeSelectedTeacherListener() {
        this.tvTeacher.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                newTeacher = this.tvTeacher.getSelectionModel().getSelectedItem();
                tfName.setText(newTeacher.getName());
                tfPaternalSurname.setText(newTeacher.getPaternalSurname());
                tfMaternalSurname.setText(newTeacher.getMaternalSurname());
                tfNumberPersonal.setText(newTeacher.getPersonalNumber() + "");
                oldPersonalNumber = newTeacher.getPersonalNumber();
                btnSaveTeacher.setDisable(true);
                btnModify.setDisable(false);
                btnDelete.setDisable(false);
                btnCancelModification.setDisable(false);
            } else {
                clearForm();
            }
        });
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
                setTeachers();
                clearForm();
            } else {
                WindowManagement.showAlert("Error", "El docente que intenta registrar ya se encuentra en el sistema", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(TeachersAdministrationController.class.getName()).log(Level.SEVERE, null, sqlException);
            WindowManagement.showAlert("Error", "Error al conectar con la base de datos", Alert.AlertType.INFORMATION);
        }
    }

    public void newModification() {
        TeacherDAO teacherDAO = new TeacherDAO();
        int result = 0;
        try {
            result = teacherDAO.modifyTeacher (newTeacher, oldPersonalNumber);
            if(result == 2) {
                WindowManagement.showAlert("Exito", "Docente modificado", Alert.AlertType.INFORMATION);
                setTeachers();
                clearForm();
                lookButtons(true);
            } else {
                WindowManagement.showAlert("Error", "El numero personal que intenta registrar ya se encuentra en el sistema", Alert.AlertType.INFORMATION);
            }
        } catch (SQLException sqlException) {
            Logger.getLogger(TeachersAdministrationController.class.getName()).log(Level.SEVERE, null, sqlException);
            WindowManagement.showAlert("Error", "Error al conectar con la base de datos", Alert.AlertType.INFORMATION);
        }
    }

    private void clearForm(){
        tfName.clear();
        tfMaternalSurname.clear();
        tfPaternalSurname.clear();
        tfNumberPersonal.clear();
    }

    private void lookButtons (boolean option){
        if (option) {
            btnModify.setDisable(true);
            btnDelete.setDisable(true);
            btnCancelModification.setDisable(true);
            btnSaveTeacher.setDisable(false);
        } else {
            btnModify.setDisable(false);
            btnDelete.setDisable(false);
            btnCancelModification.setDisable(false);
            btnSaveTeacher.setDisable(true);
        }
    }

    private boolean completedForm() {
        return tfNumberPersonal.getText().isEmpty() || tfName.getText().isEmpty() || tfPaternalSurname.getText().isEmpty() || tfMaternalSurname.getText().isEmpty();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seeSelectedTeacherListener();
        setTeachers();
        lookButtons(true);
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void saveTeacher(ActionEvent actionEvent) {
        if (completedForm()) {
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
        if (completedForm()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
        } else {
            if (validateData() == 4) {
                newModification();
            } else {
                WindowManagement.showAlert("Error", "Caracteres invalidos detectados, verifique", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void cancelModification(ActionEvent actionEvent) {
        clearForm();
        lookButtons(true);
    }

    @FXML
    public void deleteTeacher(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de confirmar la acción?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            int result = 0;
            TeacherDAO teacherDAO = new TeacherDAO();
            try {
                result = teacherDAO.deleteTeacher(newTeacher.getPersonalNumber(), newTeacher.getIdPerson());
                if (result == 1) {
                    WindowManagement.showAlert("Exito", "El registro se borró con éxito", Alert.AlertType.INFORMATION);
                }
                lookButtons(true);
            } catch (SQLException exception){
                Logger.getLogger(TeachersAdministrationController.class.getName()).log(Level.SEVERE, null, exception);
            }
            if (result == 1) {
                WindowManagement.showAlert("Exito", "Eliminacion exitosa", Alert.AlertType.INFORMATION);
            }
        }
    }
}
