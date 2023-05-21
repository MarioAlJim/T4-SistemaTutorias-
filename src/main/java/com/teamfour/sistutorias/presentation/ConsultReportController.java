package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsultReportController implements Initializable {

    @FXML
    private ComboBox<Tutorship> cbTutorship;
    @FXML
    private Label lbAssistance;
    @FXML
    private Label lbPeriod;
    @FXML
    private Label lbRisk;
    @FXML
    private TextArea taComment;
    @FXML
    private TableColumn<AcademicProblem, String> tcAcademicProblemDescription;
    @FXML
    private TableColumn<AcademicProblem, Integer> tcAcademicProblemGroup;
    @FXML
    private TableColumn<AcademicProblem, Integer> tcAcademicProblemNumTutored;
    @FXML
    private TableColumn<AcademicProblem, String> tcAcademicProblemTitle;
    @FXML
    private TableColumn<Assistance, CheckBox> tcAssistance;
    @FXML
    private TableColumn<Assistance, String> tcName;
    @FXML
    private TableColumn<Assistance, CheckBox> tcRisk;
    @FXML
    private TableView<AcademicProblem> tvAcademicProblems;
    @FXML
    private TableView<Assistance> tvTutored;
    private ObservableList<User> tutors;
    private ObservableList<Tutorship> tutorships;
    private EducativeProgram educativeProgram;
    private ObservableList<Assistance> assistances;
    private ObservableList<AcademicProblem> academicProblems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tutors = FXCollections.observableArrayList();
        tutorships = FXCollections.observableArrayList();
        assistances = FXCollections.observableArrayList();
        academicProblems = FXCollections.observableArrayList();

        ConfigureTables();
        try {
            LoadTutorships();
        } catch (SQLException sqlException) {
            Logger.getLogger(ConsultReportController.class.getName()).log(Level.SEVERE, null, sqlException);
        }

    }

    private void ConfigureTables() {
        tcAcademicProblemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcAcademicProblemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcAcademicProblemGroup.setCellValueFactory(new PropertyValueFactory<>("group"));
        tcAcademicProblemNumTutored.setCellValueFactory(new PropertyValueFactory<>("numberTutorados"));

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAssistance.setCellValueFactory(new PropertyValueFactory<>("checkBoxAsistencia"));
        tcRisk.setCellValueFactory(new PropertyValueFactory<>("checkBoxRiesgo"));
    }

    private void LoadTutorships() throws SQLException {
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        tutorships.addAll(tutorshipDAO.getTutorships());
        cbTutorship.setItems(tutorships);
        this.cbTutorship.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            this.tvAcademicProblems.getItems().clear();
            this.tvTutored.getItems().clear();
            this.taComment.clear();
            this.lbAssistance.setText("Porcentaje de asistencia:");
            this.lbRisk.setText("Porcentaje en riesgo:");
            try {
                LoadTutorshipReport(valorNuevo.getIdTutorShip());
            } catch (SQLException sqlException) {
                Logger.getLogger(ConsultReportController.class.getName()).log(Level.SEVERE, null, sqlException);
                WindowManagement.connectionLostMessage();
            }
        });

    }

    private void LoadTutorshipReport (int tutorshipId) throws SQLException {
        setPeriod(cbTutorship.getSelectionModel().getSelectedItem());
        RegisterDAO registerDAO = new RegisterDAO();
        AssistanceDAO assistanceDAO = new AssistanceDAO();
        CommentDAO commentDAO = new CommentDAO();
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();

        Register register = new Register(
                SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getEmail(),
                tutorshipId,
                SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram());

        Register tutorshipReport = registerDAO.getSpecificRegister(register);

        if (tutorshipReport.getRegister_id() == 0) {
            WindowManagement.showAlert("Atencion", "No se encontraron datos para esta tutoria", Alert.AlertType.INFORMATION);
        } else {
            ArrayList<Assistance> assistancesArrayList = assistanceDAO.getAssistancesFromRegister(tutorshipReport.getRegister_id());
            Comment comment = commentDAO.getCommentFromRegister(tutorshipReport.getRegister_id());
            ArrayList<AcademicProblem> academicProblemsArrayList = academicProblemDAO.getAcademicProblemsFromRegister(tutorshipReport.getRegister_id());
            assistances.addAll(assistancesArrayList);
            tvTutored.setItems(assistances);
            SetPercentages();
            academicProblems.addAll(academicProblemsArrayList);
            tvAcademicProblems.setItems(academicProblems);
            taComment.setText(comment.getDescription());
        }
    }

    private void setPeriod(Tutorship tutorship) throws SQLException {
        PeriodDAO periodDAO = new PeriodDAO();
        ArrayList<Period> periods = periodDAO.getPeriods();
        for (Period p : periods) {
            if (p.getIdPeriod() == tutorship.getPeriodId()) ;
            lbPeriod.setText("Periodo: " + p.getStart() + " - " + p.getEnd());
        }
    }

    private void SetPercentages() {
        double totalAssistances = assistances.size();
        double assistance = 0;
        double risk = 0;
        for (Assistance a : assistances) {
            if (a.getAsistencia()) {
                assistance++;
            }
            if (a.getRiesgo()) {
                risk++;
            }
        }
        if (totalAssistances > 0) {
            lbAssistance.setText("Porcentaje de asistencia: " + (assistance / totalAssistances) * 100 + "%");
            lbRisk.setText("Porcentaje de riesgo: " + (risk / totalAssistances) * 100 + "%");
        } else {
            lbAssistance.setText("Porcentaje de asistencia: 0%");
        }
    }

    @FXML
    void closeWindow(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }
}
