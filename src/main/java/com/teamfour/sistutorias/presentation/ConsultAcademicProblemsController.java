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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ConsultAcademicProblemsController implements Initializable{

    @FXML
    private ComboBox cbTutorship;
    @FXML
    private ComboBox cbPeriod;
    @FXML
    private TableView<AcademicProblem> tvProblems;
    @FXML
    private TableColumn<AcademicProblem, String> colTitle;
    @FXML
    private TableColumn<AcademicProblem, String> colProblem_id;
    @FXML
    private TableColumn<AcademicProblem, Integer> colNrc;
    @FXML
    private Button btnClose;
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
    private Label lblTutorados;
    @FXML
    private Label lblGroup;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblDescription;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnModiify;
    @FXML
    private Label lblSugerence;
    @FXML
    private Label lblSolution;
    private ObservableList<AcademicProblem> academicProblemData;
    private AcademicProblem academicProblem = new AcademicProblem();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPeriods();
        seeSelectedAcademicProblemListener();
    }

    private void setPeriods() {
        ArrayList<Period> periods;
        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<Period> options = FXCollections.observableArrayList();
        try {
            periods = periodDAO.getPeriods();
            options.addAll(periods);
        } catch (SQLException ex){
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbPeriod.setItems(options);
        cbPeriod.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            cbTutorship.getSelectionModel().clearSelection();
            Period selectedPeriod;
            selectedPeriod = (Period) valorNuevo;
            setTutorships(selectedPeriod);
        });
    }

    private void setTutorships(Period selectedPeriod) {
        ArrayList<Tutorship> tutorships;
        ObservableList<Tutorship> options = FXCollections.observableArrayList();
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        try {
            tutorships = tutorshipDAO.getTutorshipByPeriod(selectedPeriod.getIdPeriod());
            options.addAll(tutorships);
        }catch (SQLException exception){
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        cbTutorship.setItems(options);
        cbTutorship.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            tvProblems.getItems().clear();
            if (valorNuevo != null) {
                Tutorship newTutorship = (Tutorship) valorNuevo;
                setAcademicProblemsTable(newTutorship);
            }
        });
    }

    private void setAcademicProblemsTable(Tutorship tutorship) {
        colProblem_id.setCellValueFactory(new PropertyValueFactory<>("idAcademicProblem"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colNrc.setCellValueFactory(new PropertyValueFactory <>("group"));
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblems;
        academicProblemData = FXCollections.observableArrayList();
        try {
            academicProblems = academicProblemDAO.consultAcademicProblemsByTutor(
                    tutorship.getIdTutorShip(),
                    SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram(),
                    SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getEmail());
            //academicProblems = academicProblemDAO.consultAcademicProblemsByTutor(tutorship.getIdTutorShip(),1, "mario14");
            academicProblemData.addAll(academicProblems);
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(ConsultAcademicProblemsController.class.getName()).log(Level.SEVERE, null, exception);
        }
        tvProblems.setItems(academicProblemData);
    }

    private void seeSelectedAcademicProblemListener() {
        this.tvProblems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                academicProblem = this.tvProblems.getSelectionModel().getSelectedItem();
                tfDescription.setText(academicProblem.getDescription());
                tfGroup.setText(academicProblem.getGroup() + " " + academicProblem.getTeacher() + " " + academicProblem.getEe());
                tfSolution.setText(academicProblem.getSolution());
                tfTitle.setText(academicProblem.getTitle());
                tfTutorados.setText(academicProblem.getNumberTutorados() + "");

                btnDelete.setDisable(false);
                btnModiify.setDisable(false);
            }
        });
    }

    @FXML
    public void deleteAcademicProblem(ActionEvent actionEvent) {
        academicProblemData.remove(tvProblems.getSelectionModel().getSelectedIndex());
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("Confirmación");
        alert.setContentText("¿Estas seguro de confirmar la acción?");
        Optional<ButtonType> action = alert.showAndWait();
        if (action.get() == ButtonType.OK) {
            int result = 0;
            try {
                result = academicProblemDAO.deleteAcademicProblem(academicProblem.getIdAcademicProblem());
            } catch (SQLException exception){
                Logger.getLogger(ConsultAcademicProblemsController.class.getName()).log(Level.SEVERE, null, exception);
            }
            if (result == 1) {
                WindowManagement.showAlert("Exito", "Eliminacion exitosa", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
    public void openModificationAcademicProblem(ActionEvent actionEvent) {
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResource("ModifyAcademicProblem.fxml").openStream());
            Scene scene = new Scene(root);
            stageMenuTutor.setScene(scene);
            stageMenuTutor.setTitle("Modificar problematica academica");
            stageMenuTutor.alwaysOnTopProperty();
            stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
            ModifyAcademicProblemController modifyAcademicProblem = (ModifyAcademicProblemController) loader.getController();
            modifyAcademicProblem.recibeParameters(academicProblem);
            stageMenuTutor.show();
        } catch (IOException exception){
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(ConsultAcademicProblemsController.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
