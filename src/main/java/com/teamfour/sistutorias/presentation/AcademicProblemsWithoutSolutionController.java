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
import javafx.util.StringConverter;

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
        try {
            populateComboBoxes();
        } catch (SQLException sqlException) {
            WindowManagement.connectionLostMessage();
            WindowManagement.closeWindow(new ActionEvent());
        }
    }

    public void populateComboBoxes() throws SQLException {
        TeacherDAO teacherDAO = new TeacherDAO();
        this.teachers.addAll(teacherDAO.getTeachers());
        this.cbTeacher.setItems(teachers);
        this.cbTeacher.getSelectionModel().selectFirst();
        this.cbTeacher.setConverter(new StringConverter<Teacher>() {
            @Override
            public String toString(Teacher teacher) {
                return teacher == null ? null : teacher.getFullName();
            }

            @Override
            public Teacher fromString(String s) {
                return null;
            }
        });

        EEDAO eedao = new EEDAO();
        this.ees.addAll(eedao.getEEs());
        this.cbEE.setItems(ees);
        this.cbEE.getSelectionModel().selectFirst();
        this.cbEE.setConverter(new StringConverter<EE>() {
            @Override
            public String toString(EE ee) {
                return ee == null ? null : ee.getName();
            }

            @Override
            public EE fromString(String s) {
                return null;
            }
        });

        PeriodDAO periodDAO = new PeriodDAO();
        this.periods.addAll(periodDAO.getPeriods());
        this.cbPeriod.setItems(periods);
        this.cbPeriod.getSelectionModel().selectFirst();
        this.cbPeriod.setConverter(new StringConverter<Period>() {
            @Override
            public String toString(Period period) {
                return period == null ? null : period.getStart() + " - " + period.getEnd();
            }

            @Override
            public Period fromString(String s) {
                return null;
            }
        });
    }

    public void populateTable() throws SQLException {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        academicProblemDAO.consultAcademicProblemsByProgram(1);
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickSave(ActionEvent event) {

    }
}
