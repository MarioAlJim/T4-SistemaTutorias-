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

public class ModifyAcademicProblemController implements Initializable {
    @FXML
    private TextField tfNumberTutorados;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea tfDescription;
    @FXML
    private ComboBox cbEe;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    private AcademicProblem academicProblem;
    private Group ees;
    public void recibeParameters (AcademicProblem academicProblem){
        this.academicProblem = academicProblem;
        loadDataProblem();
        setEes(1);
        //loadEes(SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getIdProgram());
    }

    private void loadDataProblem(){
        tfDescription.setText(academicProblem.getDescription());
        tfTitle.setText(academicProblem.getTitle());
        tfNumberTutorados.setText(academicProblem.getNumberTutorados() + "");
    }

    private void setEes(int program_id) {
        ArrayList<Group> educativeExperiences;
        ObservableList<Group> educativeExperiencesObservableList = FXCollections.observableArrayList();
        try {
            GroupDAO groupDAO = new GroupDAO();
            educativeExperiences = groupDAO.groupsList(program_id);
            if (!educativeExperiences.isEmpty()) {
                educativeExperiencesObservableList.addAll(educativeExperiences);
            } else {
                WindowManagement.showAlert("Error", "No se pudieron encontrar Experiencias educactivas", Alert.AlertType.INFORMATION);
            }
            cbEe.setItems(educativeExperiencesObservableList);
            cbEe.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
                ees = (Group) valorNuevo;
            });
        } catch (SQLException exception){
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private int validateData() {
        int validData = 0;
        String cantidadTutorados = tfNumberTutorados.getText();
        if (cantidadTutorados.matches("[+-]?\\d*(\\.\\d+)?")) {
            int number = Integer.parseInt(cantidadTutorados);
            if (number > 0 && number < 30) {
                ++validData;
            }
        }
        if (tfTitle.getText().length() < 100) {
            ++validData;
        }
        if (tfDescription.getText().length() < 500) {
            ++validData;
        }
        return validData;
    }

    private void saveProblem() {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        int numberTutorados = Integer.parseInt(tfNumberTutorados.getText());
        String title = tfTitle.getText();
        String description = tfDescription.getText();
        int nrc = ees.getNrc();
        academicProblem.setNumberTutorados(numberTutorados);
        academicProblem.setDescription(description);
        academicProblem.setTitle(title);
        academicProblem.setGroup(nrc);
        try {
            int result = academicProblemDAO.updateAcademicProblem(academicProblem);
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

    @FXML
    public void updateAcademicProblem(ActionEvent actionEvent) {
        if (tfNumberTutorados.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados 1", Alert.AlertType.INFORMATION);
        } else if (tfTitle.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados 2", Alert.AlertType.INFORMATION);
        } else if (tfDescription.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "Campos vacios detectados 3", Alert.AlertType.INFORMATION);
        } else if (ees == null) {
            WindowManagement.showAlert("Error", "Campos vacios detectados 4", Alert.AlertType.INFORMATION);
        } else {
            if (validateData() == 3) {
                saveProblem();
            } else {
                WindowManagement.showAlert("Error", "Caracteres invalidos detectados", Alert.AlertType.INFORMATION);
            }
        }
    }


    @FXML
    public void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
