package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.TutoradoDAO;
import com.teamfour.sistutorias.bussinesslogic.UserRoleProgramDAO;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.Tutorado;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.cell.PropertyValueFactory;

public class TutorAssignmentController implements Initializable {
    @FXML
    private TableView<Tutorado> tvTutorados;
    @FXML
    private TableView<UserRoleProgram> tvTutores;
    @FXML
    private TableColumn<Tutorado, String> tcNumberRegister;
    @FXML
    private TableColumn<Tutorado, String> tcNameTurorado;
    @FXML
    private TableColumn<UserRoleProgram, String> tcNumPersonal;
    @FXML
    private TableColumn<UserRoleProgram, String> tcNameTutor;
    @FXML
    private TextField tfNameTutor;
    @FXML
    private TextField tfNameTutorado;
    @FXML
    private TextField tfSelectedTutor;
    @FXML
    private TextField tfSelectedTutorado;

    private final ObservableList<Tutorado> tableTutorados = FXCollections.observableArrayList();
    private final ObservableList<UserRoleProgram> tableTutors = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            // FOR DEMONSTRATION PURPOSES
            SessionGlobalData.getSessionGlobalData().getUserRoleProgram().setIdProgram(1);
            populateTutorsTable();
            populateTutoradosTable();
            seeSelectedTutorListener();
            seeSelectedTutoradoListener();
            searchTutor();
            searchTutorado();
        } catch (SQLException sqlException) {
            WindowManagement.connectionLostMessage();
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, sqlException);
        }
    }

    private void populateTutorsTable() throws SQLException {
        UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
        ArrayList<UserRoleProgram> tutors = userRoleProgramDAO.getTutorsByProgram(SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getIdProgram());
        this.tableTutors.addAll(tutors);

        this.tcNumPersonal.setCellValueFactory(new PropertyValueFactory<>("email"));
        this.tcNameTutor.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        this.tvTutores.setItems(this.tableTutors);
    }

    private void populateTutoradosTable() throws SQLException {
        TutoradoDAO tutoradoDAO = new TutoradoDAO();
        ArrayList<Tutorado> tutorados = tutoradoDAO.getTutoradosByProgramTutor(SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getIdProgram());
        this.tableTutorados.addAll(tutorados);

        this.tcNameTurorado.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        this.tcNumberRegister.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));

        this.tvTutorados.setItems(this.tableTutorados);
    }

    private void seeSelectedTutorListener() {
        this.tvTutores.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                UserRoleProgram selectedTutor = this.tvTutores.getSelectionModel().getSelectedItem();
                String tutorsName = selectedTutor.getFullName();
                this.tfSelectedTutor.setText(tutorsName);
            } else {
                this.tfSelectedTutor.clear();
            }
        });
    }

    private void seeSelectedTutoradoListener() {
        this.tvTutorados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                Tutorado selectedTutorado = this.tvTutorados.getSelectionModel().getSelectedItem();
                String tutoradosName = selectedTutorado.getFullName();
                this.tfSelectedTutorado.setText(tutoradosName);
            } else {
                this.tfSelectedTutorado.clear();
            }
        });
    }

    private void searchTutor() {
        FilteredList<UserRoleProgram> filteredTutors = new FilteredList<>(tableTutors, b-> true);
        tfNameTutor.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTutors.setPredicate(
                    tutor -> {
                        if(newValue == null || newValue.isEmpty())
                            return true;

                        String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");
                        boolean itsInTable = false;
                        if(tutor.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if(tutor.getPaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if(tutor.getMaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if(tutor.getEmail().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        }

                        return itsInTable;
                    }
            );
        });
        SortedList<UserRoleProgram> sortedTutors = new SortedList<>(filteredTutors);
        sortedTutors.comparatorProperty().bind(this.tvTutores.comparatorProperty());
        this.tvTutores.setItems(sortedTutors);
    }

    private void searchTutorado() {
        FilteredList<Tutorado> filteredTutorados = new FilteredList<>(tableTutorados, b-> true);
        tfNameTutorado.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTutorados.setPredicate(
                    tutorado -> {
                        if(newValue == null || newValue.isEmpty())
                            return true;

                        String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");
                        boolean itsInTable = false;
                        if(tutorado.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if(tutorado.getPaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if(tutorado.getMaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if(tutorado.getRegistrationNumber().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        }

                        return itsInTable;
                    }
            );
        });
        SortedList<Tutorado> sortedTutorados = new SortedList<>(filteredTutorados);
        sortedTutorados.comparatorProperty().bind(this.tvTutorados.comparatorProperty());
        this.tvTutorados.setItems(sortedTutorados);
    }

    @FXML
    public void assignTutor(ActionEvent actionEvent) {
        Tutorado selectedTutorado = this.tvTutorados.getSelectionModel().getSelectedItem();
        UserRoleProgram selectedTutor = this.tvTutores.getSelectionModel().getSelectedItem();

        if(this.tvTutores.getSelectionModel().getSelectedItem() == null) {
            WindowManagement.showAlert("Asignación no registrada",
                    "Seleccione un tutor",
                    Alert.AlertType.WARNING);
        } else if(this.tvTutorados.getSelectionModel().getSelectedItem() == null) {
            WindowManagement.showAlert("Asignación no registrada",
                    "Seleccione un tutorado",
                    Alert.AlertType.WARNING);
        } else {
            TutoradoDAO tutoradoDAO = new TutoradoDAO();
            boolean registeredAssignment;

            try {
                registeredAssignment = tutoradoDAO.assignTutor(selectedTutorado, selectedTutor);
                if(registeredAssignment) {
                    this.tableTutorados.remove(selectedTutorado);
                    this.tfNameTutorado.clear();
                    this.tfSelectedTutorado.clear();
                    this.tvTutorados.getSelectionModel().clearSelection();
                    WindowManagement.showAlert("Asignación registrada",
                            "Se ha registrado la asignación exitosamente",
                            Alert.AlertType.CONFIRMATION);
                } else {
                    WindowManagement.showAlert("Asignación no registrada",
                            "No se ha registrado la asignación",
                            Alert.AlertType.ERROR);
                }
            } catch (SQLException sqlException) {
                WindowManagement.connectionLostMessage();
                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, sqlException);
            }
        }
    }

    @FXML
    public void clickCancel(ActionEvent actionEvent) {
        WindowManagement.closeWindow(actionEvent);
    }

}
