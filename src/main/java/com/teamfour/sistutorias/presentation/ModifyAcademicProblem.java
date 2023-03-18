package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Group;
import com.teamfour.sistutorias.bussinesslogic.AcademicProblemDAO;
import com.teamfour.sistutorias.bussinesslogic.GroupDAO;
import com.teamfour.sistutorias.domain.AcademicProblem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ModifyAcademicProblem implements Initializable {
    @javafx.fxml.FXML
    private TextField txtNumberTutorados;
    @javafx.fxml.FXML
    private TextField txtTitle;
    @javafx.fxml.FXML
    private TextArea txtDescription;
    @javafx.fxml.FXML
    private ComboBox cbEe;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Button btnCancel;

    private AcademicProblem academicProblem;
    private Group ees;
    public void recibeParameters (AcademicProblem academicProblem){
        this.academicProblem = academicProblem;
        loadDataProblem();
        loadEes(1);
        //loadEes(SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getIdProgram());
    }

    private void loadDataProblem(){
        txtDescription.setText(academicProblem.getDescription());
        txtTitle.setText(academicProblem.getTitle());
        txtNumberTutorados.setText(academicProblem.getNumberTutorados() + "");
    }

    private void loadEes(int idProgram) { // este metodo ecibe el numero personal de un maestro
        ArrayList<Group> educativeExperiences;
        ObservableList<Group> educativeExperiencesObservableList = FXCollections.observableArrayList();
        try {
            GroupDAO groupDAO = new GroupDAO();
            educativeExperiences = groupDAO.groupsList(idProgram);
            if (!educativeExperiences.isEmpty()) {
                educativeExperiencesObservableList.addAll(educativeExperiences);
            }
            cbEe.setItems(educativeExperiencesObservableList);
            cbEe.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
                ees = (Group) valorNuevo;
            });
        } catch (SQLException exception){ //excepcion de sqL controlada
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.WARNING);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private int validateData() {
        int validData = 0;
        String cantidadTutorados = txtNumberTutorados.getText();
        if (cantidadTutorados.matches("[+-]?\\d*(\\.\\d+)?")) {
            int number = Integer.parseInt(cantidadTutorados);
            if (number > 0 && number < 30) {
                ++validData;
            }
        }
        if (txtTitle.getText().length() < 100) {
            ++validData;
        }
        if (txtDescription.getText().length() < 500) {
            ++validData;
        }
        return validData;
    }

    private void saveProblem() {
        AcademicProblem academicProblem = new AcademicProblem();
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        int numberTutorados = Integer.parseInt(txtNumberTutorados.getText());
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        int nrc = 12345; //ees.getNrc();
        academicProblem.setNumberTutorados(numberTutorados);
        academicProblem.setDescription(description);
        academicProblem.setTitle(title);
        academicProblem.setGroup(nrc);
        academicProblem.setRegister(1);
        try {
            int result = academicProblemDAO.register(academicProblem);
            if (result == 1) {
                WindowManagement.showAlert("Exito", "Actualizacion realizada", Alert.AlertType.INFORMATION);
            } else {
                WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            }
        }catch (SQLException exception){
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @javafx.fxml.FXML
    public void updateAcademicProblem(ActionEvent actionEvent) {
        if (txtNumberTutorados.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
        } else if (txtTitle.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
        } else if (txtDescription.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
        } else if (ees != null) {
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
        } else {
            if (validateData() == 3) {
                saveProblem();
            } else {
                WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
            }
        }
    }


    @javafx.fxml.FXML
    public void close(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
