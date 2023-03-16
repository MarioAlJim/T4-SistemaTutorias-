package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.AssistanceDAO;
import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.domain.Assistance;
import com.teamfour.sistutorias.domain.Period;
import com.teamfour.sistutorias.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    private ObservableList<Assistance> tutorados;

    @FXML
    void closeWindow(ActionEvent event) {
        Scene scene = lbAssistantPercentage.getScene();
        Stage stage = (Stage) scene.getWindow();
        stage.close();
    }

    @FXML
    void openAddComment(ActionEvent event) {

    }

    @FXML
    void openRegisterProblem(ActionEvent event) {

    }

    @FXML
    void saveRegister(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Remove after testing
        tutor = new User();
        tutor.setEmail("test@test.com");

        setUpTable();
        loadTutored();
        updatePercentage();
        setPeriod();
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
}
