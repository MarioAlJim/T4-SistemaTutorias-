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
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ConsultTutorshipReport implements Initializable {

    @FXML
    private ComboBox<User> cbTutor;

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
    private TableColumn<?, ?> tcAcademicProblemDescription;

    @FXML
    private TableColumn<?, ?> tcAcademicProblemGroup;

    @FXML
    private TableColumn<?, ?> tcAcademicProblemNumTutored;

    @FXML
    private TableColumn<?, ?> tcAcademicProblemTitle;

    @FXML
    private TableColumn<?, ?> tcAssistance;

    @FXML
    private TableColumn<?, ?> tcName;

    @FXML
    private TableColumn<?, ?> tcRisk;

    @FXML
    private TableView<AcademicProblem> tvAcademicProblems;

    @FXML
    private TableView<Tutorado> tvTutored;

    private ObservableList<User> tutors;
    private ObservableList<Tutorship> tutorships;
    private EducationProgram educationProgram;
    private ObservableList<Assistance> assistances;
    private ObservableList<AcademicProblem> academicProblems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Remove after testing
        educationProgram = new EducationProgram();
        educationProgram.setIdEducationProgram(1);

        tutors = FXCollections.observableArrayList();
        tutorships = FXCollections.observableArrayList();
        assistances = FXCollections.observableArrayList();
        academicProblems = FXCollections.observableArrayList();

        ConfigureTables();
        LoadTutors();
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

    private void LoadTutors() {
        UserDAO userDAO = new UserDAO();
        try {
            tutors.addAll(userDAO.getTutors(educationProgram.getIdEducationProgram()));
            cbTutor.setItems(tutors);
            cbTutor.setOnAction(event -> LoadTutorships());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadTutorships() {
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        try {
            tutorships.addAll(tutorshipDAO.getTutorships());
            cbTutorship.setItems(tutorships);
            cbTutorship.setOnAction(event -> LoadTutorshipReport());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void LoadTutorshipReport() {
        int tutorshipId = cbTutorship.getSelectionModel().getSelectedItem().getIdTutorShip();
        RegisterDAO registerDAO = new RegisterDAO();
        AssistanceDAO assistanceDAO = new AssistanceDAO();
        CommentDAO commentDAO = new CommentDAO();
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        try {
            Register tutorshipReport = registerDAO.getTutorshipRegister(tutorshipId);
            if (tutorshipReport == null || tutorshipReport.getRegister_id() == 0) {
                new Alert(Alert.AlertType.ERROR, "No se ha encontrado el reporte de tutoría").showAndWait();
            } else {
                ArrayList<Assistance> assistancesArrayList = assistanceDAO.getAssistancesFromRegister(tutorshipReport.getRegister_id());
                Comment comment = commentDAO.getCommentFromRegister(tutorshipReport.getRegister_id());
                ArrayList<AcademicProblem> academicProblemsArrayList = academicProblemDAO.getAcademicProblemsFromRegister(tutorshipReport.getRegister_id());
                academicProblems.addAll(academicProblemsArrayList);
                if (assistancesArrayList != null && !assistancesArrayList.isEmpty()) {
                    assistances.addAll(assistancesArrayList);
                    for (Assistance a : assistances) {
                        if (a.getAsistencia()) {
                            a.getCheckBoxAsistencia().setSelected(true);
                        }
                        if (a.getRiesgo()) {
                            a.getCheckBoxRiesgo().setSelected(true);
                        }
                    }
                    tvTutored.setItems(FXCollections.observableArrayList(assistances));
                    SetPercentages();
                } else {
                    new Alert(Alert.AlertType.ERROR, "No se han encontrado asistencias").showAndWait();
                }
                if (academicProblemsArrayList != null && !academicProblemsArrayList.isEmpty()) {
                    academicProblems.addAll(academicProblemsArrayList);
                    tvAcademicProblems.setItems(FXCollections.observableArrayList(academicProblems));
                } else {
                    new Alert(Alert.AlertType.ERROR, "No se han encontrado problemas académicos").showAndWait();
                }
                if (comment.getDescription() != null && !comment.getDescription().isEmpty()) {
                    taComment.setText(comment.getDescription());
                } else {
                    new Alert(Alert.AlertType.ERROR, "No se ha encontrado el comentario").showAndWait();
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            new Alert(Alert.AlertType.ERROR, "Error al cargar el reporte de tutoría").showAndWait();
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
            System.out.println(risk);
        } else {
            lbAssistance.setText("Porcentaje de asistencia: 0%");
        }

    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) cbTutor.getScene().getWindow();
        stage.close();
    }
}
