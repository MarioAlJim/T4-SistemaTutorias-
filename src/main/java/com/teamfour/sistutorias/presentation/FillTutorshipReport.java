package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FillTutorshipReport implements Initializable {

    @FXML
    private Label lbAssistantPercentage;

    @FXML
    private Label lbDate;

    @FXML
    private Label lbPeriod;

    @FXML
    private Label lbRiskPercentage;

    @FXML
    private TableColumn<Assistance, CheckBox> tcAssistance;

    @FXML
    private TableColumn<Assistance, CheckBox> tcRisk;

    @FXML
    private TableColumn tcTutored;

    @FXML
    private TableView<Assistance> tvTutored;

    private User tutor;
    private Tutorship tutorship;
    private Comment comment;
    private ArrayList<AcademicProblem> academicProblems;
    private ObservableList<Assistance> tutorados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Remove after testing
        tutor = new User();
        tutor.setEmail("test@test.com");

        setUpTable();
        loadTutored();
        updatePercentage();
        setPeriod();
        setTutorship();

        comment = new Comment();
        academicProblems = new ArrayList<>();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Scene scene = lbAssistantPercentage.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    @FXML
    void openAddComment(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Agregar comentario");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("AddComment.fxml"))));
            stage.initModality(Modality.APPLICATION_MODAL);
            AddComment addComment = new AddComment();
            addComment.setComment(comment);
            stage.showAndWait();
            comment = addComment.getComment();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openRegisterProblem(ActionEvent event) {
        /* TODO: Create a new window to register academic problems
        Stage stage = new Stage();
        stage.setTitle("Registrar problema académico");
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("RegisterAcademicProblem.fxml"))));
            stage.initModality(Modality.APPLICATION_MODAL);
            RegisterAcademicProblem registerAcademicProblem = new RegisterAcademicProblem();
            registerAcademicProblem.setAcademicProblems(academicProblems);
            stage.showAndWait();
            academicProblems = registerAcademicProblem.getAcademicProblems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
    }

    @FXML
    void saveRegister(ActionEvent event) {
        Register register = new Register();
        register.setEmail(tutor.getEmail());
        register.setTutorship_id(tutorship.getIdTutorShip());
        register.setEducative_program_id(1); //TODO: Change in order to avoid magic numbers

        RegisterDAO registerDAO = new RegisterDAO();
        try {
            registerDAO.register(register);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            register = registerDAO.getLatestRegister();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(register.getRegister_id() != 0) {
            comment.setRegister(register.getRegister_id());
            CommentDAO commentDAO = new CommentDAO();
            try {
                commentDAO.register(comment);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            for(AcademicProblem academicProblem : academicProblems) {
                academicProblem.setRegister(register.getRegister_id());
                AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
                try {
                    academicProblemDAO.register(academicProblem);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void setUpTable() {
        tcTutored.setCellValueFactory(new PropertyValueFactory<Assistance, String>("name"));
        tcAssistance.setCellValueFactory(new PropertyValueFactory<>("checkBoxAsistencia"));
        tcRisk.setCellValueFactory(new PropertyValueFactory<>("checkBoxRiesgo"));
        tutorados = FXCollections.observableArrayList();
    }

    private void loadTutored() {
        AssistanceDAO assistanceDAO = new AssistanceDAO();
        try {
            ArrayList<Assistance> queryResult = new ArrayList<>(assistanceDAO.getAssistanceTutor(tutor.getEmail()));
            if(queryResult != null) {
                tutorados.addAll(queryResult);
                tvTutored.getItems().clear();
                tvTutored.setItems(tutorados);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!tutorados.isEmpty()) {
            for (Assistance tutored : tutorados) {
                tutored.getCheckBoxAsistencia().setOnAction(event -> updatePercentage());
                tutored.getCheckBoxRiesgo().setOnAction(event -> updatePercentage());
            }
        }
    }

    private void updatePercentage() {
        int total = tutorados.size();
        int assistance = 0;
        int risk = 0;
        for (Assistance tutored : tutorados) {
            if(tutored.getCheckBoxAsistencia().isSelected()) {
                assistance++;
            }
            if(tutored.getCheckBoxRiesgo().isSelected()) {
                risk++;
            }
        }
        lbAssistantPercentage.setText("Porcentaje de asistencia: " + "\t" + (assistance * 100 / total) + "%");
        lbRiskPercentage.setText("Porcentaje en riesgo:        " + "\t" + (risk * 100 / total) + "%");
    }

    private void setPeriod() {
        PeriodDAO periodDAO = new PeriodDAO();
        try {
            ArrayList<Period> periods = new ArrayList<>(periodDAO.getPeriods());
            lbPeriod.setText("Periodo: " +
                    periods.get(periods.size() - 1).getStart() +
                    " - " +
                    periods.get(periods.size() - 1).getEnd());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setTutorship() {
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        try {
            tutorship = tutorshipDAO.getLatestTutorship();
            lbDate.setText("Fecha de sesion de tutoria: " + tutorship.getStart());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
