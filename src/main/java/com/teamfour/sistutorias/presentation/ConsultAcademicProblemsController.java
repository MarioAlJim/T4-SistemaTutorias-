package com.teamfour.sistutorias.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.teamfour.sistutorias.bussinesslogic.AcademicProblemDAO;
import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.bussinesslogic.TutorshipDAO;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import com.teamfour.sistutorias.domain.AcademicProblem;
import com.teamfour.sistutorias.domain.Period;
import com.teamfour.sistutorias.domain.Tutorship;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConsultAcademicProblemsController implements Initializable {

    @FXML
    private ComboBox<Tutorship> cbTutorship;
    @FXML
    private ComboBox<Period> cbPeriod;
    @FXML
    private TableView<AcademicProblem> tvProblems;
    @FXML
    private TableColumn<AcademicProblem, String> tcTitle;
    @FXML
    private TableColumn<AcademicProblem, Integer> tcNrc;
    @FXML
    private TextField tfTutorados;
    @FXML
    private TextField tfGroup;
    @FXML
    private TextField tfSolution;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfDescription;
    @FXML
    private TableColumn<AcademicProblem, String> tcDescription;
    @FXML
    private Button btDelete;
    @FXML
    private Button btModify;
    @FXML
    private Button btClose;
    private ObservableList<AcademicProblem> academicProblemData;
    private AcademicProblem academicProblem = new AcademicProblem();
    private Period selectedPeriod;
    private Tutorship selectedTutorship;

    private void setPeriods() {
        ArrayList<Period> periods;
        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<Period> options = FXCollections.observableArrayList();
        try {
            periods = periodDAO.getPeriods();
            options.addAll(periods);
        } catch (SQLException ex) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbPeriod.setItems(options);
        cbPeriod.valueProperty().addListener((ov, oldValue, newValue) -> {
            cbTutorship.getSelectionModel().clearSelection();
            tvProblems.getSelectionModel().clearSelection();
            selectedPeriod = newValue;
            setTutorship();
        });
    }

    private void setTutorship() {
        ArrayList<Tutorship> tutorship;
        ObservableList<Tutorship> tutorships = FXCollections.observableArrayList();
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        try {
            tutorship = tutorshipDAO.getTutorshipByPeriod(selectedPeriod.getIdPeriod());
            tutorships.addAll(tutorship);
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        cbTutorship.setItems(tutorships);
        cbTutorship.valueProperty().addListener((ov, oldValue, newValue) -> {
            tvProblems.getItems().clear();
            if (newValue != null) {
                selectedTutorship = newValue;
                setAcademicProblemsTable();
            }
        });
    }

    private void setAcademicProblemsTable() {
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcNrc.setCellValueFactory(new PropertyValueFactory<>("group"));
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblems;
        academicProblemData = FXCollections.observableArrayList();
        try {
            academicProblems = academicProblemDAO.consultAcademicProblemsByTutor(
                    selectedTutorship.getIdTutorShip(),
                    SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram(),
                    SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getEmail());
            academicProblemData.addAll(academicProblems);
            btDelete.setDisable(true);
            btModify.setDisable(true);
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(ConsultAcademicProblemsController.class.getName()).log(Level.SEVERE, null, exception);
        }
        tvProblems.setItems(academicProblemData);

        this.tvProblems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                academicProblem = this.tvProblems.getSelectionModel().getSelectedItem();
                tfDescription.setText(academicProblem.getDescription());
                tfGroup.setText(academicProblem.getGroup() + " " + academicProblem.getTeacher() + " " + academicProblem.getEe());
                tfSolution.setText(academicProblem.getSolution());
                tfTitle.setText(academicProblem.getTitle());
                tfTutorados.setText(String.valueOf(academicProblem.getNumberTutorados()));

                if (selectedTutorship.getIdTutorShip() == SessionGlobalData.getSessionGlobalData().getCurrentTutorship().getIdTutorShip()) {
                    btDelete.setDisable(false);
                    btModify.setDisable(false);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPeriods();
    }

    @FXML
    public void deleteAcademicProblem() {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de confirmar la eliminacion?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            int result = 0;
            try {
                result = academicProblemDAO.deleteAcademicProblem(academicProblem.getIdAcademicProblem());
                academicProblemData.remove(tvProblems.getSelectionModel().getSelectedIndex());
                tvProblems.getSelectionModel().clearSelection();
                tfDescription.setText("");
                tfTitle.setText("");
                tfTutorados.setText("");
                tfSolution.setText("");
                tfGroup.setText("");
            } catch (SQLException exception) {
                Logger.getLogger(ConsultAcademicProblemsController.class.getName()).log(Level.SEVERE, null, exception);
            }
            if (result == 1) {
                WindowManagement.showAlert("Exito", "Eliminacion exitosa", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void openModificationAcademicProblem() {
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ModifyAcademicProblem.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stageMenuTutor.setScene(scene);
            stageMenuTutor.setTitle("Modificar problematica academica");
            stageMenuTutor.alwaysOnTopProperty();
            stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
            ModifyAcademicProblemController modifyAcademicProblem = (ModifyAcademicProblemController) loader.getController();
            modifyAcademicProblem.recibeParameters(academicProblem);
            stageMenuTutor.showAndWait();
            tvProblems.getItems().clear();
            setAcademicProblemsTable();
        } catch (IOException exception) {
            WindowManagement.showAlert("Error", "Error al cargar la ventana de modificacion", Alert.AlertType.INFORMATION);
            Logger.getLogger(ConsultAcademicProblemsController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @FXML
    public void close() {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
}
