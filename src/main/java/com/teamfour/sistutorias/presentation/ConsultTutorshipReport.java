package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.TutorshipDAO;
import com.teamfour.sistutorias.bussinesslogic.UserDAO;
import com.teamfour.sistutorias.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Remove after testing
        educationProgram = new EducationProgram();
        educationProgram.setIdEducationProgram(1);

        tutors = FXCollections.observableArrayList();
        tutorships = FXCollections.observableArrayList();

        ConfigureTables();
        LoadTutors();
    }

    private void ConfigureTables() {
        tcAcademicProblemTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tcAcademicProblemDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcAcademicProblemGroup.setCellValueFactory(new PropertyValueFactory<>("group"));
        tcAcademicProblemNumTutored.setCellValueFactory(new PropertyValueFactory<>("numberTutorados"));

        tcName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcAssistance.setCellValueFactory(new PropertyValueFactory<>("assistance"));
        tcRisk.setCellValueFactory(new PropertyValueFactory<>("risk"));

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
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) cbTutor.getScene().getWindow();
        stage.close();
    }
}
