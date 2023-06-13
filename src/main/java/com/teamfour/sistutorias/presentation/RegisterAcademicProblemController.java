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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class RegisterAcademicProblemController implements Initializable{

    @FXML
    private TextField tfNumberTutorados;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextArea tfDescription;
    @FXML
    private ComboBox<Group> cbGroups;
    @FXML
    private Button btSave;
    @FXML
    private TableView<AcademicProblem> tvAcademicProblems;
    @FXML
    private TableColumn<AcademicProblem, String> tcTitle;
    @FXML
    private TableColumn<AcademicProblem, String> tcDescription;
    @FXML
    private TableColumn<AcademicProblem, Integer> tcNrc;
    @FXML
    private Button btClose;
    @FXML
    private Button btDelete;
    @FXML
    private Button btModify;
    @FXML
    private Button btCancel;

    private Group ees;
    private ArrayList<AcademicProblem> listAcademicProblems;
    private ObservableList<AcademicProblem> academicProblems = FXCollections.observableArrayList();
    private int indexAcademicProblemSelected;


    public void setListAcademicProblems(ArrayList<AcademicProblem> academicProblems){
        this.listAcademicProblems = academicProblems;
        setTableAcademicProblems();
    }

    public ArrayList<AcademicProblem> getListAcademicProblems(){
        return listAcademicProblems;
    }

    private void setTableAcademicProblems() {
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcNrc.setCellValueFactory(new PropertyValueFactory<>("group"));

        this.academicProblems.addAll(listAcademicProblems);
        this.tvAcademicProblems.setItems(academicProblems);
        this.tvAcademicProblems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                indexAcademicProblemSelected = tvAcademicProblems.getSelectionModel().getSelectedIndex();
                tfTitle.setText(newSelection.getTitle());
                tfDescription.setText(newSelection.getDescription());
                tfNumberTutorados.setText(String.valueOf(newSelection.getNumberTutorados()));
                lockButtons(false);
            }
        });
    }

    private void setGroups() {
        ArrayList<Group> educativeExperiences;
        ObservableList<Group> educativeExperiencesObservableList = FXCollections.observableArrayList();
        try {
            Group voidGroup = new Group();
            educativeExperiencesObservableList.add(voidGroup);
            GroupDAO groupDAO = new GroupDAO();
            educativeExperiences = groupDAO.getGroupsList(
                    SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducativeProgram(),
                    SessionGlobalData.getSessionGlobalData().getCurrentPeriod().getIdPeriod());
            educativeExperiencesObservableList.addAll(educativeExperiences);
            cbGroups.setItems(educativeExperiencesObservableList);
            cbGroups.getSelectionModel().selectFirst();
            cbGroups.valueProperty().addListener((ov, oldValue, newValue) -> {
                ees = newValue;
            });
        } catch (SQLException exception){
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private boolean completedForm() {
        boolean complete = true;
        if (tfDescription.getText().isEmpty() || tfDescription.getText().trim().replaceAll(" +", "").length() == 0)
            complete = false;
        if (tfTitle.getText().isEmpty() || tfTitle.getText().trim().replaceAll(" +", "").length() == 0)
            complete = false;
        if (cbGroups.getSelectionModel().isSelected(0))
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

    private void lockButtons (boolean lock) {
        if (lock) {
            tfDescription.setText("");
            tfTitle.setText("");
            tfNumberTutorados.setText("");
            cbGroups.getSelectionModel().selectFirst();
            btCancel.setDisable(true);
            btModify.setDisable(true);
            btDelete.setDisable(true);
            btSave.setDisable(false);
        } else {
            btCancel.setDisable(false);
            btModify.setDisable(false);
            btDelete.setDisable(false);
            btSave.setDisable(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setGroups();
        lockButtons(true);
    }

    @FXML
    public void saveAcademicProblem() {
        if (completedForm()) {
            if (validData()) {
                AcademicProblem academicProblem = new AcademicProblem();
                int numberTutorados = Integer.parseInt(tfNumberTutorados.getText().trim().replaceAll(" +", " "));
                String title = tfTitle.getText().trim().replaceAll(" +", " ");
                String description = tfDescription.getText().trim().replaceAll(" +", " ");
                int idGroup = ees.getGroup_id();
                academicProblem.setNumberTutorados(numberTutorados);
                academicProblem.setDescription(description);
                academicProblem.setTitle(title);
                academicProblem.setGroup(idGroup);
                this.listAcademicProblems.add(academicProblem);
                this.academicProblems.add(academicProblem);
                lockButtons(true);
            } else {
                WindowManagement.showAlert("Atencion", "Se detectó el uso de caracteres invalidos, numero de tutorados debe ser un numero, en titulo y descripcion no se permiten caracteres especiales", Alert.AlertType.INFORMATION);
            }
        } else {
            WindowManagement.showAlert("Atencion", "Todos los campos deben estar llenos", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void deleteAcademicProblem() {
        academicProblems.remove(indexAcademicProblemSelected);
        listAcademicProblems.remove(indexAcademicProblemSelected);
        lockButtons(true);
    }

    @FXML
    public void modifyAcademicProblem() {
        if (completedForm()) {
            if (validData()) {
                AcademicProblem academicProblem = new AcademicProblem();
                int numberTutorados = Integer.parseInt(tfNumberTutorados.getText().trim().replaceAll(" +", ""));
                String title = tfTitle.getText().trim().replaceAll(" +", "");
                String description = tfDescription.getText().trim().replaceAll(" +", "");
                int idGroup = ees.getGroup_id();
                academicProblem.setNumberTutorados(numberTutorados);
                academicProblem.setDescription(description);
                academicProblem.setTitle(title);
                academicProblem.setGroup(idGroup);
                academicProblems.remove(indexAcademicProblemSelected);
                listAcademicProblems.remove(indexAcademicProblemSelected);
                listAcademicProblems.add(academicProblem);
                academicProblems.add(academicProblem);
                lockButtons(true);
            } else {
                WindowManagement.showAlert("Atencion", "Se detectó el uso de caracteres invalidos, numero de tutorados debe ser un numero, en titulo y descripcion no se permiten caracteres especiales", Alert.AlertType.INFORMATION);
            }
        } else {
            WindowManagement.showAlert("Atencion", "Todos los campos deben estar llenos", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void cancel() {
        lockButtons(true);
    }

    @FXML
    public void close() {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }
}
