package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.AcademicProblemDAO;
import com.teamfour.sistutorias.bussinesslogic.EEDAO;
import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.domain.EE;
import com.teamfour.sistutorias.domain.Period;
import com.teamfour.sistutorias.domain.Teacher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AcademicProblemsWithoutSolutionController implements Initializable {

    @javafx.fxml.FXML
    private ComboBox cbEE;
    @FXML
    private TableView<AcademicProblemsTable> tvAcademicProblems;
    @javafx.fxml.FXML
    private TableColumn<AcademicProblemsTable, CheckBox> tcCheckbox;
    @javafx.fxml.FXML
    private TableColumn<AcademicProblemsTable, String> tcAcademicProblem;
    @javafx.fxml.FXML
    private TableColumn<AcademicProblemsTable, String> tcTeacher;
    @javafx.fxml.FXML
    private TableColumn<AcademicProblemsTable, String> tcEE;
    @javafx.fxml.FXML
    private ComboBox cbTeacher;
    @javafx.fxml.FXML
    private ComboBox cbPeriod;
    @javafx.fxml.FXML
    private TextArea taAcademicProblem;
    @javafx.fxml.FXML
    private TextArea taSolution;

    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private ObservableList<Period> periods = FXCollections.observableArrayList();
    private ObservableList<EE> ees = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void populateComboBoxes() throws SQLException {
        TeacherDAO teacherDAO = new TeacherDAO();
        this.teachers.addAll(teacherDAO.getTeachers());
        this.cbTeacher.setItems(teachers);

        EEDAO eedao = new EEDAO();
        this.ees.addAll(eedao.getEEs());
        this.cbEE.setItems(ees);

        PeriodDAO periodDAO = new PeriodDAO();
        this.periods.addAll(periodDAO.getPeriods());
        this.cbPeriod.setItems(periods);
    }

    public void populateTable() throws SQLException {
        TeacherDAO teacherDAO = new TeacherDAO();
        EEDAO eedao = new EEDAO();
        PeriodDAO periodDAO = new PeriodDAO();
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();

    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickSave(ActionEvent event) {

    }
}
