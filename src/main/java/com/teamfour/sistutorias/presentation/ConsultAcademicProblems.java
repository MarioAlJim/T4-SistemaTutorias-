package com.teamfour.sistutorias.presentation;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
//listo
public class ConsultAcademicProblems implements Initializable{

    @javafx.fxml.FXML
    private ComboBox cbTutorship;
    @javafx.fxml.FXML
    private ComboBox cbPeriod;
    @javafx.fxml.FXML
    private TableView<AcademicProblem> tblProblems;
    @javafx.fxml.FXML
    private TableColumn colIdProblem;
    @javafx.fxml.FXML
    private TableColumn colNrc;
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
    private TableColumn colTitle;

    Messages alerts = new Messages();

    private void definePeriods() {
        ArrayList<Period> periods;
        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<Period> options;
        options = FXCollections.observableArrayList();
        try {
            periods = periodDAO.getPeriods();
            for(Period period : periods){
                options.add(period);
            }
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
            for (Tutorship tutorshipCicles : tutorships) {
                options.add(tutorshipCicles);
            }
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
        colIdProblem.setCellValueFactory(new PropertyValueFactory<AcademicProblem, String>("idAcademicProblem"));
        colTitle.setCellValueFactory(new PropertyValueFactory<AcademicProblem, String>("title"));
        colNrc.setCellValueFactory(new PropertyValueFactory <AcademicProblem, Integer>("group"));
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblems;
        ObservableList<AcademicProblem> showAcademicProblems = FXCollections.observableArrayList();
        try {
            academicProblems = academicProblemDAO.consultAcademicProblemsByTutor(1, 1, "mario14");
            for (AcademicProblem problems : academicProblems) {
                showAcademicProblems.add(problems);
            }
        } catch (SQLException exception) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
        tblProblems.setItems(showAcademicProblems);
    }

    private final ListChangeListener<AcademicProblem> selectProblem =
            new ListChangeListener<AcademicProblem>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends AcademicProblem> c) {
                    loadDataProbleam();
                }
            };

    public AcademicProblem getproblem() {
        AcademicProblem academicProblem = new AcademicProblem();
        if (tblProblems != null) {
            List<AcademicProblem> tabla = tblProblems.getSelectionModel().getSelectedItems();
            if (tabla.size() == 1) {
                academicProblem = tabla.get(0);
                System.out.println(academicProblem);
            }
        }
        return academicProblem;
    }

    private void loadDataProbleam(){
        AcademicProblem academicProblem = getproblem();
        txtDescription.setText(academicProblem.getDescription());
        txtGroup.setText(academicProblem.getGroup() + "");
        txtSolution.setText(academicProblem.getSolution());
        txtTitle.setText(academicProblem.getTitle());
        txtTutorados.setText(academicProblem.getNumberTutorados() + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        definePeriods();
        ObservableList<AcademicProblem> problems = tblProblems.getSelectionModel().getSelectedItems();
        problems.addListener(selectProblem);
    }

    @javafx.fxml.FXML
    public void delete(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void startModificate(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
