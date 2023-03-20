package com.teamfour.sistutorias.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.collections.ListChangeListener;
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


public class ConsultAcademicProblems implements Initializable{

    @javafx.fxml.FXML
    private ComboBox cbTutorship;
    @javafx.fxml.FXML
    private ComboBox cbPeriod;
    @javafx.fxml.FXML
    private TableView<AcademicProblem> tblProblems;
    @javafx.fxml.FXML
    private TableColumn<AcademicProblem, String> colIdProblem;
    @javafx.fxml.FXML
    private TableColumn<AcademicProblem, Integer> colNrc;
    @javafx.fxml.FXML
    private Button btnClose;
    @javafx.fxml.FXML
    private TextField txtTutorados;
    @javafx.fxml.FXML
    private TextField txtGroup;
    @javafx.fxml.FXML
    private TextField txtSolution;
    @javafx.fxml.FXML
    private TextField txtTitle;
    @javafx.fxml.FXML
    private TextField txtDescription;
    @javafx.fxml.FXML
    private Label lblTutorados;
    @javafx.fxml.FXML
    private Label lblGroup;
    @javafx.fxml.FXML
    private Label lblTitle;
    @javafx.fxml.FXML
    private Label lblDescription;
    @javafx.fxml.FXML
    private Button btnDelete;
    @javafx.fxml.FXML
    private Button btnModiify;
    @javafx.fxml.FXML
    private Label lblSugerence;
    @javafx.fxml.FXML
    private Label lblSolution;
    @FXML
    private TableColumn<AcademicProblem, String> colTitle;
    private ObservableList<AcademicProblem> showAcademicProblems;
    private AcademicProblem academicProblem = new AcademicProblem();
    Messages alerts = new Messages();

    private void definePeriods() {
        ArrayList<Period> periods;
        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<Period> options;
        options = FXCollections.observableArrayList();
        try {
            periods = periodDAO.getPeriods();
            options.addAll(periods);
        } catch (SQLException ex){
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        cbPeriod.setItems(options);
        cbPeriod.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            cbTutorship.getSelectionModel().clearSelection();
            Period selectedPeriod;
            selectedPeriod = (Period) valorNuevo;
            showTutorships(selectedPeriod);
        });
    }

    private void showTutorships(Period selectedPeriod) {
        try {
            ObservableList<Tutorship> options;
            options = FXCollections.observableArrayList();
            TutorshipDAO tutorshipDAO = new TutorshipDAO();
            ArrayList<Tutorship> tutorships = tutorshipDAO.getTutorshipByPeriod(selectedPeriod.getIdPeriod());
            options.addAll(tutorships);
            cbTutorship.setItems(options);
            cbTutorship.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
                tblProblems.getItems().clear();
                if (valorNuevo != null) {
                    Tutorship newTutorship = (Tutorship) valorNuevo;
                    showProblems(newTutorship);
                }
            });
        }catch (SQLException exception){
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    private void showProblems(Tutorship tutorship) {
        colIdProblem.setCellValueFactory(new PropertyValueFactory<>("idAcademicProblem"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colNrc.setCellValueFactory(new PropertyValueFactory <>("group"));
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblems;
        showAcademicProblems = FXCollections.observableArrayList();
        try {
            academicProblems = academicProblemDAO.consultAcademicProblemsByTutor(1, 1, "mario14");
            showAcademicProblems.addAll(academicProblems);
        } catch (SQLException exception) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        tblProblems.setItems(showAcademicProblems);
    }

    private final ListChangeListener<AcademicProblem> selectProblem =
            c -> loadDataProblem();

    public AcademicProblem getproblem() {
        if (tblProblems != null) {
            List<AcademicProblem> tabla = tblProblems.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                academicProblem = tabla.get(0);
            }
        }
        return academicProblem;
    }

    private void loadDataProblem(){
        AcademicProblem academicProblem = getproblem();
        txtDescription.setText(academicProblem.getDescription());
        txtGroup.setText(academicProblem.getGroup() + " " + academicProblem.getTeacher() + " " + academicProblem.getEe());
        txtSolution.setText(academicProblem.getSolution());
        txtTitle.setText(academicProblem.getTitle());
        txtTutorados.setText(academicProblem.getNumberTutorados() + "");

        btnDelete.setDisable(false);
        btnModiify.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        definePeriods();
        ObservableList<AcademicProblem> problems = tblProblems.getSelectionModel().getSelectedItems();
        problems.addListener(selectProblem);
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        showAcademicProblems.remove(tblProblems.getSelectionModel().getSelectedIndex());
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
                Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
            }
            if (result == 1) {
                WindowManagement.showAlert("Exito", "Eliminacion exitosa", Alert.AlertType.INFORMATION);
            }
        }
    }

    @javafx.fxml.FXML
    public void startModificate(ActionEvent actionEvent) {
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResource("ModifyAcademicProblem.fxml").openStream());
            Scene scene = new Scene(root);
            stageMenuTutor.setScene(scene);
            stageMenuTutor.setTitle("Modificar problematica academica");
            stageMenuTutor.alwaysOnTopProperty();
            stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
            ModifyAcademicProblem modifyAcademicProblem = (ModifyAcademicProblem) loader.getController();
            modifyAcademicProblem.recibeParameters(academicProblem);
            stageMenuTutor.show();
        } catch (IOException exception){
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @javafx.fxml.FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
