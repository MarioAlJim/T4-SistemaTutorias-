package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.domain.*;
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
import java.util.HashMap;
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
    private TableView<AcademicProblem> tvAcademicProblems;

    @FXML
    private TableView<Comment> tvGeneralComment;

    ObservableList<Period> periods;
    ObservableList<Tutorship> tutorships;
    HashMap<String, String> tutorsComments;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tutorsComments = new HashMap<>();
        LoadPeriods();
        SetUpTables();
        cbTutorship.setDisable(true);
    }

    private void LoadPeriods() {
        periods = FXCollections.observableArrayList();
        PeriodDAO periodDAO = new PeriodDAO();

        try {
            periods.addAll(periodDAO.getPeriods());
            cbPeriod.setItems(periods);
            cbPeriod.addEventHandler(ActionEvent.ACTION, event -> {
                tvAcademicProblems.getItems().clear();
                tvGeneralComment.getItems().clear();
                cbTutorship.setDisable(false);
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
            if (tutorships.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "No hay tutorías en este periodo").showAndWait();
                return;
            }
            cbTutorship.setItems(tutorships);
            cbTutorship.addEventHandler(ActionEvent.ACTION, event -> {
                tvAcademicProblems.getItems().clear();
                tvGeneralComment.getItems().clear();
                LoadTutorshipReports();
                lbTutorshipDate.setText("Fecha de Tutoria: " +
                        cbTutorship.getSelectionModel().getSelectedItem().getStart());
            });
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar las tutorías").showAndWait();
            e.printStackTrace();
        }
    }

    private void LoadTutorshipReports() {
        RegisterDAO RegisterDAO = new RegisterDAO();
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        CommentDAO commentDAO = new CommentDAO();
        AssistanceDAO assistanceDAO = new AssistanceDAO();

        int totalAssistances = 0;
        int totalRisks = 0;
        int totalStudents = 0;

        try {
            List<Register> tutorshipReports = new ArrayList<>(RegisterDAO.getTutorshipRegister(cbTutorship.getSelectionModel().getSelectedItem().getIdTutorShip()));


            List<AcademicProblem> academicProblems = new ArrayList<>();
            for (Register tutorshipReport : tutorshipReports) {
                tutorsComments.put(tutorshipReport.getEmail(), commentDAO.getCommentFromRegister(tutorshipReport.getRegister_id()).getDescription());
                academicProblems.addAll(academicProblemDAO.getAcademicProblemsFromRegister(tutorshipReport.getRegister_id()));

                List<Assistance> attendance = assistanceDAO.getAssistancesFromRegister(tutorshipReport.getRegister_id());
                for (Assistance assistance : attendance) {
                    totalStudents++;
                    lbTutoredTotal.setText("Total de Tutorados: " + totalStudents);
                    if (assistance.getAsistencia()) {
                        totalAssistances++;

                    }
                    if (assistance.getRiesgo()) {
                        totalRisks++;
                    }
                }
            }
            if (totalStudents != 0){
                lbTutoredAttendance.setText("Porcentaje de Asistencia: " + (totalAssistances * 100) / totalStudents + "%");
                lbTutoredRisk.setText("Porcentaje en Riesgo: " + (totalRisks * 100) / totalStudents + "%");
            } else {
                lbTutoredAttendance.setText("Porcentaje de Asistencia: 0%");
                lbTutoredRisk.setText("Porcentaje en Riesgo: 0%");
            }



            generateComments();
            tvAcademicProblems.setItems(FXCollections.observableArrayList(academicProblems));
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar los reportes de tutoría").showAndWait();
            e.printStackTrace();
        }
    }

    private void generateComments() {
        List<Comment> comments = new ArrayList<>();
        for (String tutor : tutorsComments.keySet()) {
            Comment comment = new Comment();
            comment.setTutor(tutor);
            comment.setDescription(tutorsComments.get(tutor));
            comments.add(comment);
        }
        tvGeneralComment.setItems(FXCollections.observableArrayList(comments));
    }

    private void SetUpTables() {
        tcTutor.setCellValueFactory(new PropertyValueFactory<>("tutor"));
        tcComment.setCellValueFactory(new PropertyValueFactory<>("description"));

        tcTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        tcNumberStudents.setCellValueFactory(new PropertyValueFactory<>("numberTutorados"));
        tcEducativeExperience.setCellValueFactory(new PropertyValueFactory<>("ee"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }
}
