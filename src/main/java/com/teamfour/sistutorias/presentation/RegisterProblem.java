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
public class RegisterProblem implements Initializable{

    @FXML
    private TextField txtNumberTutorados;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextArea txtDescription;
    @FXML
    private ComboBox cbEe;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    Messages alerts = new Messages();
    Group ees;

    @FXML
    public void newRegister(ActionEvent event) {
        if(txtNumberTutorados.getText().isEmpty()){
            alerts.mostrarAlertaCamposVacios();
        }else if(txtTitle.getText().isEmpty()){
            alerts.mostrarAlertaCamposVacios();
        }else if(txtDescription.getText().isEmpty()){
            alerts.mostrarAlertaCamposVacios();
        }else if(ees != null){
            alerts.mostrarAlertaCamposVacios();
        }else {
            if(validateData() == 3){
                saveProblem();
            } else {
                alerts.mostrarCamposInvalidos();
            }
        }
    }

    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void loadEes(int idProgram) { // este metodo ecibe el numero personal de un maestro
        ArrayList<Group> educativeExperiences;
        ObservableList<Group> educativeExperiencesObservableList = FXCollections.observableArrayList();
        try {
            GroupDAO groupDAO = new GroupDAO();
            educativeExperiences = groupDAO.groupsList(idProgram);
            if (!educativeExperiences.isEmpty()) {
                for (Group ee : educativeExperiences) {
                    educativeExperiencesObservableList.add(ee);
                }
            } else {
                alerts.mostrarAlertaNoHayExperienciasRegistradas();
            }
            cbEe.setItems(educativeExperiencesObservableList);
            cbEe.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
                ees = (Group) valorNuevo;
            });
        } catch (SQLException exception){ //excepcion de sqL controlada
            alerts.mostrarAlertaErrorConexionDB();
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }


    private int validateData() {
        int validData = 0;
        String cantidadTutorados = txtNumberTutorados.getText();
        if(cantidadTutorados.matches("[+-]?\\d*(\\.\\d+)?")) {
            int number = Integer.parseInt(cantidadTutorados);
            if(number > 0 && number < 30) {
                ++validData;
            }
        }
        if(txtTitle.getText().length() < 100) {
            ++validData;
        }
        if(txtDescription.getText().length() < 500){
            ++validData;
        }
        /*if(ees != null){
            ++validData;
        }*/
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
                alerts.mostrarAlertaRegistroExitoso();
            } else {
                alerts.mostrarAlertaRegistroNoCompletado();
            }
        }catch (SQLException exception){
            alerts.mostrarAlertaErrorConexionDB();
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEes(1);
    }

    private void clean(ActionEvent event) {
        txtDescription.setText("");
        txtTitle.setText("");
        txtNumberTutorados.setText("");
        cbEe.getSelectionModel().clearSelection();
    }
}
