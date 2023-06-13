package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
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
    private TableColumn<Object, Object> tcPersonalNum;
    @FXML
    private TableColumn<Object, Object> tcName;
    @FXML
    private Button btnDeleteGroup;
    @FXML
    private Button btnModifyGroup;
    @FXML
    private Button btnCancelModification;
    @FXML
    private TableView<Teacher> tvTeacher;
    @FXML
    private TableColumn<Teacher, String> tcNameTeacher;
    @FXML
    private TableView<EE> tvEE;
    @FXML
    private TableColumn<EE, String> tcEE;
    @FXML
    private TextField tfSelectedEE;
    @FXML
    private TextField tfSelectedTeacher;
    @FXML
    private ComboBox<EducativeProgram> cbEducationProgram;
    @FXML
    private TableColumn<Group, Integer> tcNrc;
    @FXML
    private TableColumn<Group, String> tcEeG;
    @FXML
    private Label lbl_action;
    @FXML
    private Label lbPeriod;
    private EducativeProgram selectedEducativeProgram;
    private Teacher selectedTeacher;
    private EE selectedEe;
    private Group newGroup = new Group();


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

    private void clearForm() {
        tfNRC.setText("");
        tfSelectedEE.setText("");
        tfSelectedTeacher.setText("");
    }

    private boolean completedForm(){
        boolean complete = true;
        String nrc = tfNRC.getText().trim().replaceAll(" +","");
        if(DataValidation.numberValidation(nrc) == -1) {
            complete = false;
            WindowManagement.showAlert("Atencion", "El NRC debe ser un numero", Alert.AlertType.INFORMATION);
        }
        if(tfSelectedEE.getText().isEmpty() || tfSelectedTeacher.getText().isEmpty()) {
            complete = false;
            WindowManagement.showAlert("Error", "Por favor seleccione todos los elementos para el registro", Alert.AlertType.INFORMATION);
        }
        return complete;
    }

    private void setAcademicPrograms() {
        EducativeProgramDAO educationProgramDAO = new EducativeProgramDAO();
        ArrayList<EducativeProgram> educativePrograms;
        ObservableList<EducativeProgram> educativeProgramsList = FXCollections.observableArrayList();
        try {
            educativePrograms = educationProgramDAO.getEducativePrograms();
            educativeProgramsList.addAll(educativePrograms);
        } catch (SQLException sqlException) {
            Logger.getLogger(GroupAdministrationController.class.getName()).log(Level.SEVERE, null, sqlException);
        }
        cbEducationProgram.setItems(educativeProgramsList);
        cbEducationProgram.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            selectedEducativeProgram = valorNuevo;
            clearForm();
            setGroups();
            lockModify(true);
            newGroup.setEducationProgram(selectedEducativeProgram);
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
        tcEeG.setCellValueFactory(new PropertyValueFactory<>("experience"));
        tcNrc.setCellValueFactory(new PropertyValueFactory<>("nrc"));
        tcName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        ArrayList<Group> groups;
        ObservableList<Group> groupsList = FXCollections.observableArrayList();
        try {
            GroupDAO groupDAO = new GroupDAO();
            groups = groupDAO.getGroupsList(selectedEducativeProgram.getIdEducativeProgram(),
                    SessionGlobalData.getSessionGlobalData().getCurrentPeriod().getIdPeriod());
            groupsList.addAll(groups);
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        tvGroup.setItems(groupsList);
    }

    private void seeSelectedTeacherListener() {
        this.tvTeacher.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedTeacher = this.tvTeacher.getSelectionModel().getSelectedItem();
                String teachersName = String.valueOf(selectedTeacher.getFullName());
                this.tfSelectedTeacher.setText(teachersName);
                newGroup.setTeacher(selectedTeacher);
            }
        });
    }

    private void seeSelectedEeListener() {
        this.tvEE.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedEe = this.tvEE.getSelectionModel().getSelectedItem();
                String eesName = String.valueOf(selectedEe.getName());
                this.tfSelectedEE.setText(eesName);
                newGroup.setEe(selectedEe);
            }
        });
    }

    private void seeSelectedGroup() {
        this.tvGroup.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                newGroup = this.tvGroup.getSelectionModel().getSelectedItem();
                this.tfNRC.setText((String.valueOf(newGroup.getNrc())));
                this.tfSelectedEE.setText(String.valueOf(newGroup.getEe().getIdEe()));
                this.tfSelectedTeacher.setText(String.valueOf(newGroup.getTeacher().getPersonalNumber()));
                lockModify(false);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setAcademicPrograms();
        setTeachers();
        setEes();
        cbEducationProgram.getSelectionModel().selectFirst();
        lockModify(true);
        btnSaveGroup.setDisable(true);
        tfSelectedTeacher.setDisable(true);
        tfSelectedEE.setDisable(true);
        seeSelectedTeacherListener();
        seeSelectedEeListener();
        seeSelectedGroup();

        lbPeriod.setText("Periodo: " + SessionGlobalData.getSessionGlobalData().getCurrentPeriod().getFullPeriod());
    }

    @FXML
    public void cancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelModification() {
        clearForm();
        lockModify(true);
    }

    @FXML
    public void registerGroup() {
        GroupDAO groupDAO = new GroupDAO();
        int result;
        if (completedForm()) {
            try {
                newGroup.setNrc(Integer.parseInt(tfNRC.getText().trim().replaceAll(" ","")));
                result = groupDAO.registerGroup(newGroup);
                if (result == 1) {
                    setGroups();
                    clearForm();
                    WindowManagement.showAlert("Éxito", "Registro exitoso", Alert.AlertType.INFORMATION);
                } else if (result == -1) {
                    WindowManagement.showAlert("Error", "El grupo que intenta registrar ya se encuentra en el sistema", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException sqlException) {
                Logger.getLogger(GroupAdministrationController.class.getName()).log(Level.SEVERE, null, sqlException);
                WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void deleteGroup() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de confirmar la eliminacion?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            GroupDAO groupDAO = new GroupDAO();
            int result;
            try {
                result = groupDAO.deleteGroup(newGroup.getGroup_id());
                if (result == 1) {
                    setGroups();
                    clearForm();
                    lockModify(true);
                    WindowManagement.showAlert("Éxito", "Eliminacion exitosa", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException sqlException) {
                Logger.getLogger(GroupAdministrationController.class.getName()).log(Level.SEVERE, null, sqlException);
                WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void modifyGroup() {
        GroupDAO groupDAO = new GroupDAO();
        int result;
        if (completedForm()) {
            try {
                int newNrc= Integer.parseInt(tfNRC.getText().trim().replaceAll(" +",""));
                result = groupDAO.modifyGroup(newGroup, newNrc);
                if (result == 1) {
                    setGroups();
                    clearForm();
                    lockModify(true);
                    WindowManagement.showAlert("Éxito", "Modificacion exitosa", Alert.AlertType.INFORMATION);
                } else if (result == -1) {
                    WindowManagement.showAlert("Error", "El grupo que intenta registrar ya se encuentra en el sistema", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException sqlException) {
                Logger.getLogger(GroupAdministrationController.class.getName()).log(Level.SEVERE, null, sqlException);
                WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            }
        } else {
            WindowManagement.showAlert("Error", "Por favor llene todos los elementos para el registro", Alert.AlertType.INFORMATION);
        }
    }
}
