package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.bussinesslogic.RegisterDAO;
import com.teamfour.sistutorias.bussinesslogic.TutorshipDAO;
import com.teamfour.sistutorias.domain.Period;
import com.teamfour.sistutorias.domain.Register;
import com.teamfour.sistutorias.domain.Tutorship;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConsultGeneralTutorshipReport implements Initializable {

    @FXML
    private ComboBox<Period> cbPeriod;

    @FXML
    private ComboBox<Tutorship> cbTutorship;

    @FXML
    private Label lbTutoredAttendance;

    @FXML
    private Label lbTutoredRisk;

    @FXML
    private Label lbTutoredTotal;

    @FXML
    private Label lbTutorshipDate;

    @FXML
    private TableColumn tcComment;

    @FXML
    private TableColumn tcDescription;

    @FXML
    private TableColumn tcEducativeExperience;

    @FXML
    private TableColumn tcNumberStudents;

    @FXML
    private TableColumn tcTeacher;

    @FXML
    private TableColumn tcTitle;

    @FXML
    private TableColumn tcTutor;

    @FXML
    private TableView tvAcademicProblems;

    @FXML
    private TableView tvGeneralComment;

    ObservableList<Period> periods;
    ObservableList<Tutorship> tutorships;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadPeriods();
        SetUpTables();
    }

    private void LoadPeriods() {
        periods = FXCollections.observableArrayList();
        PeriodDAO periodDAO = new PeriodDAO();

        try {
            periods.addAll(periodDAO.getPeriods());
            cbPeriod.setItems(periods);
            cbPeriod.getSelectionModel().selectFirst();
            cbPeriod.addEventHandler(ActionEvent.ACTION, event -> {
                LoadTutorships();
            });
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar los periodos").showAndWait();
            e.printStackTrace();
        }
    }

    private void LoadTutorships() {
        tutorships = FXCollections.observableArrayList();
        TutorshipDAO tutorshipDAO = new TutorshipDAO();

        try {
            tutorships.addAll(tutorshipDAO.getTutorshipByPeriod(cbPeriod.getSelectionModel().getSelectedItem().getIdPeriod()));
            cbTutorship.setItems(tutorships);
            cbTutorship.getSelectionModel().selectFirst();
            cbTutorship.addEventHandler(ActionEvent.ACTION, event -> {
                LoadTutorshipReports();
            });
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar las tutorías").showAndWait();
            e.printStackTrace();
        }
    }

    private void LoadTutorshipReports() {
        List<Register> tutorshipReports = new ArrayList<Register>();
        RegisterDAO RegisterDAO = new RegisterDAO();

        try {
            tutorshipReports.addAll(RegisterDAO.getTutorshipRegister(cbTutorship.getSelectionModel().getSelectedItem().getIdTutorShip()));
            tvAcademicProblems.setItems(FXCollections.observableArrayList(tutorshipReports));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar los reportes de tutoría").showAndWait();
            e.printStackTrace();
        }
    }

    private void SetUpTables() {
        tcTutor.setCellValueFactory(new PropertyValueFactory<>("tutor"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

        tcTeacher.setCellValueFactory(new PropertyValueFactory<>("docente"));
        tcNumberStudents.setCellValueFactory(new PropertyValueFactory<>("numbertutorados"));
        tcEducativeExperience.setCellValueFactory(new PropertyValueFactory<>("educativeExperience"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
    }
}
