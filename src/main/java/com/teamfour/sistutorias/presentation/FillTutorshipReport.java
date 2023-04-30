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
import javafx.scene.control.*;
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
    private ObservableList<Assistance> tutorados = FXCollections.observableArrayList();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddComment.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            AddComment addComment = loader.getController();
            addComment.setComment(comment);
            stage.showAndWait();
            comment = addComment.getComment();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void openRegisterProblem(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Registrar problema académico");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterAcademicProblem.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            RegisterAcademicProblemController registerAcademicProblem = loader.getController();
            registerAcademicProblem.setListAcademicProblems(academicProblems);
            stage.showAndWait();
            academicProblems = registerAcademicProblem.getListAcademicProblems();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

            register = registerDAO.getLatestRegister();

            if(register.getRegister_id() != 0) {
                System.out.println("Register saved");

                comment.setRegister(register.getRegister_id());
                CommentDAO commentDAO = new CommentDAO();
                commentDAO.register(comment);
                System.out.println("Comment saved");

                for(AcademicProblem academicProblem : academicProblems) {
                    academicProblem.setRegister(register.getRegister_id());
                    AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
                    academicProblemDAO.register(academicProblem);
                }
                System.out.println("Academic problems saved");

                for(Assistance tutored : tutorados) {
                    AssistanceDAO assistanceDAO = new AssistanceDAO();
                    tutored.setRegister_id(register.getRegister_id());
                    if (tutored.getCheckBoxAsistencia().isSelected()) {
                        tutored.setAsistencia(true);
                    } else {
                        tutored.setAsistencia(false);
                    }
                    if (tutored.getCheckBoxRiesgo().isSelected()) {
                        tutored.setRiesgo(true);
                    } else {
                        tutored.setRiesgo(false);
                    }
                    assistanceDAO.register(tutored);
                }
                System.out.println("Assistance saved");
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al guardar el registro").showAndWait();
            System.err.println(e.getMessage());
        }

        new Alert(Alert.AlertType.INFORMATION, "Registro guardado").showAndWait();
        closeWindow(event);
    }

    private void setUpTable() {
        tcTutored.setCellValueFactory(new PropertyValueFactory<Assistance, String>("name"));
        tcAssistance.setCellValueFactory(new PropertyValueFactory<>("checkBoxAsistencia"));
        tcRisk.setCellValueFactory(new PropertyValueFactory<>("checkBoxRiesgo"));
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
        int total = 1; tutorados.size();
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
