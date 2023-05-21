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
    private ComboBox<Group> cbGroup;
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
    private AcademicProblem academicProblem;
    private Group ees;

    public void recibeParameters (AcademicProblem academicProblem){
        this.academicProblem = academicProblem;
        tfDescription.setText(academicProblem.getDescription());
        tfTitle.setText(academicProblem.getTitle());
        tfNumberTutorados.setText(String.valueOf(academicProblem.getNumberTutorados()));
        setGroups();
    }

    private void setGroups() {
        ArrayList<Group> educativeExperiences;
        ObservableList<Group> educativeExperiencesObservableList = FXCollections.observableArrayList();
        try {
            Group voidGroup = new Group();
            educativeExperiencesObservableList.add(voidGroup);
            GroupDAO groupDAO = new GroupDAO();
            educativeExperiences = groupDAO.getGroupsList(
                    SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram(),
                    SessionGlobalData.getSessionGlobalData().getCurrentPeriod().getIdPeriod());
            educativeExperiencesObservableList.addAll(educativeExperiences);
            cbGroup.setItems(educativeExperiencesObservableList);
            cbGroup.getSelectionModel().selectFirst();
            cbGroup.valueProperty().addListener((ov, oldValue, newValue) -> {
                ees = newValue;
            });
        } catch (SQLException exception){
            WindowManagement.connectionLostMessage();
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private boolean completedForm() {
        boolean complete = true;
        if (tfDescription.getText().isEmpty() || tfDescription.getText().trim().replaceAll(" +", "").length() == 0)
            complete = false;
        if (tfTitle.getText().isEmpty() || tfTitle.getText().trim().replaceAll(" +", "").length() == 0)
            complete = false;
        if (cbGroup.getSelectionModel().isSelected(0))
            complete = false;
        if (tfNumberTutorados.getText().isEmpty() || tfNumberTutorados.getText().trim().replaceAll(" +", "").length() == 0)
            complete = false;

        return complete;
    }

    private boolean validData() {
        boolean validData = true;
        String numberTutorado = tfNumberTutorados.getText().trim().replaceAll(" +","");
        if(DataValidation.numberValidation(numberTutorado) == -1) {
            validData = false;
        }
        if(tfTitle.getText().trim().replaceAll(" +", "").length() > 100 || !DataValidation.textValidation(tfTitle.getText())) {
            validData = false;
        }
        if(tfDescription.getText().trim().replaceAll(" +", "").length() > 500 || !DataValidation.textValidation(tfDescription.getText())) {
            validData = false;
        }
        return validData;
    }

    @FXML
    public void updateAcademicProblem() {
        if (completedForm()) {
            if (validData()) {
                AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
                int numberTutorados = Integer.parseInt(tfNumberTutorados.getText().trim().replaceAll(" +", ""));
                String title = tfTitle.getText().trim().replaceAll(" +", "");
                String description = tfDescription.getText().trim().replaceAll(" +", "");
                int idGroup = ees.getGroup_id();
                academicProblem.setNumberTutorados(numberTutorados);
                academicProblem.setDescription(description);
                academicProblem.setTitle(title);
                academicProblem.setGroup(idGroup);
                try {
                    int result = academicProblemDAO.updateAcademicProblem(academicProblem);
                    if (result == 1) {
                        WindowManagement.showAlert("Exito", "Actualizacion realizada", Alert.AlertType.INFORMATION);
                    }
                }catch (SQLException exception){
                    WindowManagement.connectionLostMessage();
                    Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
                }
            } else {
                WindowManagement.showAlert("Atencion", "Se detectó el uso de caracteres invalidos, numero de tutorados debe ser un numero, en titulo y descripcion no se permiten caracteres especiales", Alert.AlertType.INFORMATION);
            }
        } else {
            WindowManagement.showAlert("Atencion", "Todos los campos deben estar llenos", Alert.AlertType.INFORMATION);
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
