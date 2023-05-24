package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ModifyAsignmentTutorTutoradoController implements Initializable {
    @FXML
    private TableView<Tutorado> tvTutorados;
    @FXML
    private TableView<UserRoleProgram> tvTutors;
    @FXML
    private TableColumn<Tutorado, String> colNumberRegister;
    @FXML
    private TableColumn<Tutorado, String> colNameTurorado;
    @FXML
    private TableColumn<UserRoleProgram, String> colNumPersonal;
    @FXML
    private TableColumn<UserRoleProgram, String> colNameTutor;
    @FXML
    private TextField tfNameTutor;
    @FXML
    private TextField tfNameTutorado;
    @FXML
    private TextField tfSelectedTutor;
    @FXML
    private TextField tfSelectedTutorado;
    @FXML
    private Button btnModifyAsignament;
    @FXML
    private Button btnClose;
    private ObservableList<Tutorado> tutoradosData;
    private ObservableList<UserRoleProgram> tutorsData;
    private UserRoleProgram selectedTutor = new UserRoleProgram();
    private Tutorado selectedTutorado = new Tutorado();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTutors();
        setTutorados();
        seeSelectedTutorListener();
        seeSelectedTutoradoListener();
        searchTutor();
        searchTutorado();
    }

    private void setTutorados() {
        colNameTurorado.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNumberRegister.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        TutoradoDAO tutoradoDAO = new TutoradoDAO();
        ArrayList<Tutorado> tutorados = new ArrayList<>();
        tutoradosData = FXCollections.observableArrayList();
        try {
            tutorados = tutoradoDAO.getTutoradosWithTutor(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducativeProgram());
        } catch (SQLException exception) {
            WindowManagement.connectionLostMessage();
            close();
        }
        tutoradosData.addAll(tutorados);
        tvTutorados.setItems(tutoradosData);
    }

    private void setTutors() {
        colNameTutor.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colNumPersonal.setCellValueFactory(new PropertyValueFactory<>("email"));
        UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
        ArrayList<UserRoleProgram> tutors = new ArrayList<>();
        tutorsData = FXCollections.observableArrayList();
        try {
            tutors = userRoleProgramDAO.getTutorsByProgram(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducativeProgram());
        } catch (SQLException exception) {
            WindowManagement.connectionLostMessage();
            close();
        }
        tutorsData.addAll(tutors);
        tvTutors.setItems(tutorsData);
    }

    private void seeSelectedTutorListener() {
        this.tvTutors.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedTutor = this.tvTutors.getSelectionModel().getSelectedItem();
                String tutorsName = selectedTutor.getFullName();
                this.tfSelectedTutor.setText(tutorsName);
            } else {
                this.tfSelectedTutor.clear();
            }
        });
    }

    private void seeSelectedTutoradoListener() {
        this.tvTutorados.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedTutorado = this.tvTutorados.getSelectionModel().getSelectedItem();
                String tutoradosName = selectedTutorado.getFullName();
                this.tfSelectedTutorado.setText(tutoradosName);
            } else {
                this.tfSelectedTutorado.clear();
            }
        });
    }

    @FXML
    public void searchTutorado() {
        FilteredList<Tutorado> filteredTutorados = new FilteredList<>(tutoradosData, b -> true);
        tfNameTutorado.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTutorados.setPredicate(
                    tutorado -> {
                        if (newValue == null || newValue.isEmpty())
                            return true;
                        String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");
                        boolean itsInTable = false;
                        if (tutorado.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if (tutorado.getPaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if (tutorado.getMaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if (tutorado.getRegistrationNumber().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
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
    private void searchTutor() {
        FilteredList<UserRoleProgram> filteredTutors = new FilteredList<>(tutorsData, b -> true);
        tfNameTutor.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredTutors.setPredicate(
                    tutor -> {
                        if (newValue == null || newValue.isEmpty())
                            return true;
                        String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");
                        boolean itsInTable = false;
                        if (tutor.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if (tutor.getPaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if (tutor.getMaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        } else if (tutor.getEmail().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                            itsInTable = true;
                        }
                        return itsInTable;
                    }
            );
        });
        SortedList<UserRoleProgram> sortedTutors = new SortedList<>(filteredTutors);
        sortedTutors.comparatorProperty().bind(this.tvTutors.comparatorProperty());
        this.tvTutors.setItems(sortedTutors);
    }

    @FXML
    public void modifyAsignament(ActionEvent actionEvent) {
        TutoradoDAO tutoradoDAO = new TutoradoDAO();
        int result;
        if (!tfSelectedTutorado.getText().isEmpty() && !tfSelectedTutor.getText().isEmpty()) {
            try {
                result = tutoradoDAO.updateTutor(selectedTutorado, selectedTutor.getEmail());
                if (result == 1) {
                    WindowManagement.showAlert("Exito", "Asignacion actualizada", Alert.AlertType.INFORMATION);
                } else {
                    System.out.println(result);
                }
            } catch (SQLException exception) {
                WindowManagement.connectionLostMessage();
                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
            }
        } else {
            WindowManagement.showAlert("Atencion", "Debe seleccionar un tutor y un tutoradop para cambiar la asignacion", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void close() {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}