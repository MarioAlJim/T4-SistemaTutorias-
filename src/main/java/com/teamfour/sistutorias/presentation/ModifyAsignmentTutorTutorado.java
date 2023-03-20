package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.teamfour.sistutorias.bussinesslogic.TutoradoDAO;
import com.teamfour.sistutorias.bussinesslogic.UserRoleProgramDAO;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorado;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyAsignmentTutorTutorado implements Initializable {
    @javafx.fxml.FXML
    private TableView<Tutorado> tblTutorados;
    @javafx.fxml.FXML
    private TableView<UserRoleProgram> tblTutores;
    @FXML
    private TableColumn<Tutorado, String> colNumberRegister;
    @FXML
    private TableColumn<Tutorado, String> colNameTurorado;
    @FXML
    private TableColumn<UserRoleProgram, String> colNumPersonal;
    @FXML
    private TableColumn<UserRoleProgram, String> colNameTutor;
    @FXML
    private TextField txtNameTutor;
    @FXML
    private TextField txtNameTutorado;
    @FXML
    private Button btnSearchTutor;
    @FXML
    private Button btnSearchtutorado;
    @FXML
    private TextField txtSelectecTutor;
    @FXML
    private TextField txtSelectedTutorado;
    @FXML
    private Button btnModifyAsignament;
    @FXML
    private Button btnClose;

    private ObservableList<Tutorado> showTutorados;
    private ObservableList<UserRoleProgram> showTutores;
    private UserRoleProgram userRoleProgram= new UserRoleProgram();
    private Tutorado tutorado = new Tutorado();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNameTurorado.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNumberRegister.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        colNameTutor.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNumPersonal.setCellValueFactory(new PropertyValueFactory<>("email"));
        ObservableList<Tutorado> tutorados = tblTutorados.getSelectionModel().getSelectedItems();
        tutorados.addListener(selectTutorado);
        ObservableList<UserRoleProgram> tutors = tblTutores.getSelectionModel().getSelectedItems();
        tutors.addListener(selectTutor);
        setTutor(1);
        setTutorado(1);
    }

    private void setTutorado(int typeSearch) {
        TutoradoDAO tutoradoDAO = new TutoradoDAO();
        ArrayList<Tutorado> tutorados = new ArrayList<>();
        showTutorados = FXCollections.observableArrayList();
        try {
            if (typeSearch == 1){
                tutorados = tutoradoDAO.getTutoradosByProgramTutor(1); //(SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getIdProgram());
            }
            else {
                tutorados = tutoradoDAO.getTutoradosByNameProgramTutor(txtNameTutorado.getText(),1);
            }
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        showTutorados.addAll(tutorados);
        tblTutorados.setItems(showTutorados);
    }

    private void setTutor(int typeSearch) {
        UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
        ArrayList<UserRoleProgram> tutors = new ArrayList<>();
        showTutores = FXCollections.observableArrayList();
        try {
            if (typeSearch == 1){
                tutors = userRoleProgramDAO.getTutorsByProgram(1); //(SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getIdProgram());
            }
            else {
                tutors = userRoleProgramDAO.getTutorsByProgramName(txtNameTutor.getText(),1);
            }
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        showTutores.addAll(tutors);
        tblTutores.setItems(showTutores);
    }

    @FXML
    public void modifyAsignament(ActionEvent actionEvent) {
            TutoradoDAO tutoradoDAO = new TutoradoDAO();
            int result;
        if (!txtSelectedTutorado.getText().isEmpty() && ! txtSelectecTutor.getText().isEmpty()){
            try {
                result = tutoradoDAO.updateTutor(tutorado, userRoleProgram.getEmail());
                if (result == 1){
                    WindowManagement.showAlert("Exito", "Asignacion actualizada", Alert.AlertType.INFORMATION);
                }
            } catch (SQLException exception) {
                WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
    }

    private final ListChangeListener<UserRoleProgram> selectTutor =
            c -> loadTutor();

    public void loadTutor() {
        if (tblTutores != null) {
            List<UserRoleProgram> tabla = tblTutores.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                userRoleProgram = tabla.get(0);
            }
        }
        txtSelectecTutor.setText(userRoleProgram.getFullName());
    }

    private final ListChangeListener<Tutorado> selectTutorado =
            c -> loadTutorado();

    public void loadTutorado() {
        if (tblTutores != null) {
            List<Tutorado> tabla = tblTutorados.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                tutorado = tabla.get(0);
            }
        }
        txtSelectedTutorado.setText(tutorado.getFullName());
    }
    @FXML
    public void close(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void searchTutorado(ActionEvent actionEvent) {
        setTutorado(2);
    }

    @FXML
    public void searchTutor(ActionEvent actionEvent) {
        setTutor(2);
    }
}
