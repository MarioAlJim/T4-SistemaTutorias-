package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Group;
import com.teamfour.sistutorias.bussinesslogic.GroupDAO;
import com.teamfour.sistutorias.domain.AcademicProblem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private Group ees;
    private ArrayList<AcademicProblem> listAcademicProblems;

    public void setListAcademicProblems(ArrayList<AcademicProblem> academicProblems){
        this.listAcademicProblems = academicProblems;
    }
    public ArrayList getListAcademicProblems(){
        return listAcademicProblems;
    }

    @FXML
    public void newRegister(ActionEvent event) {
        if(txtNumberTutorados.getText().isEmpty()){
            WindowManagement.showAlert("Error", "Campos vacios detectados 1", Alert.AlertType.INFORMATION);
        }else if(txtTitle.getText().isEmpty()){
            WindowManagement.showAlert("Error", "Campos vacios detectados 2", Alert.AlertType.INFORMATION);
        }else if(txtDescription.getText().isEmpty()){
<<<<<<< HEAD
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
        }else if(ees == null){
            WindowManagement.showAlert("Error", "Campos vacios detectados", Alert.AlertType.INFORMATION);
=======
            WindowManagement.showAlert("Error", "Campos vacios detectados 3", Alert.AlertType.INFORMATION);
        }else if(ees == null){
            WindowManagement.showAlert("Error", "Campos vacios detectados 4", Alert.AlertType.INFORMATION);
>>>>>>> main
        }else {
            if(validateData() == 3){
                saveProblem();
            } else {
                WindowManagement.showAlert("Error", "Se detectó el uso de caracteres invalidos", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void close(ActionEvent event) {
        exitWindow();
    }

    private void exitWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void loadEes(int idProgram) {
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
        } catch (SQLException exception){
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private int validateData() {
        int validData = 0;
        String cantidadTutorados = txtNumberTutorados.getText();
        if(cantidadTutorados.matches("[0-9]+")) {
            int number = Integer.parseInt(cantidadTutorados);
            if(number > 0 && number < 30) {
                ++validData;
            }
        }
        if(txtTitle.getText().length() < 100) {
            ++validData;
        }
        if(txtDescription.getText().length() < 500) {
            ++validData;
        }
        return validData;
    }

    private void saveProblem() {
        AcademicProblem academicProblem = new AcademicProblem();
        int numberTutorados = Integer.parseInt(txtNumberTutorados.getText());
        String title = txtTitle.getText();
        String description = txtDescription.getText();
        int nrc = ees.getNrc();
        academicProblem.setNumberTutorados(numberTutorados);
        academicProblem.setDescription(description);
        academicProblem.setTitle(title);
        academicProblem.setGroup(nrc);
        academicProblem.setRegister(1);
        listAcademicProblems.add(academicProblem);
        exitWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEes(1);
    }

    private void clean() {
        txtDescription.setText("");
        txtTitle.setText("");
        txtNumberTutorados.setText("");
        cbEe.getSelectionModel().clearSelection();
        if (listAcademicProblems == null)
            listAcademicProblems = new ArrayList<>();
    }
}
