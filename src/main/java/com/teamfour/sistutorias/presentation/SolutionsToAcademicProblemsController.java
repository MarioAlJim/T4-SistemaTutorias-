package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.AcademicProblemDAO;
import com.teamfour.sistutorias.bussinesslogic.EEDAO;
import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.domain.AcademicProblem;
import com.teamfour.sistutorias.domain.EE;
import com.teamfour.sistutorias.domain.Period;
import com.teamfour.sistutorias.domain.Teacher;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SolutionsToAcademicProblemsController implements Initializable {

    @FXML
    private ComboBox<Teacher> cbTeacher;
    @FXML
    private ComboBox<EE> cbEE;
    @FXML
    private ComboBox<Period> cbPeriod;
    @FXML
    private TextArea taSolution;
    @FXML
    private TableView<SolutionsTable> tvAcademicProblems;
    @FXML
    private TableColumn<SolutionsTable, ComboBox> tcAcademicProblem;
    @FXML
    private TableColumn<SolutionsTable, String> tcTeacher;
    @FXML
    private TableColumn<SolutionsTable, String> tcEE;

    private ObservableList<SolutionsTable> tableSolutions = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBoxes();
            populateTable();
            seeSolutionListener();
        } catch (SQLException sqlException) {
            WindowManagement.connectionLostMessage();
        }
    }

    private void populateComboBoxes() throws SQLException {
        TeacherDAO teacherDAO = new TeacherDAO();
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        teachers.add(new Teacher());
        teachers.addAll(teacherDAO.getTeachers());
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
        ObservableList<EE> ees = FXCollections.observableArrayList();
        ees.add(new EE());
        ees.addAll(eedao.getEEs());
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
        ObservableList<Period> periods = FXCollections.observableArrayList();
        periods.add(new Period());
        periods.addAll(periodDAO.getPeriods());
        this.cbPeriod.setItems(periods);
        this.cbPeriod.getSelectionModel().selectFirst();
        this.cbPeriod.setConverter(new StringConverter<Period>() {
            @Override
            public String toString(Period period) {
                return period == null ? null : period.getFullPeriod();
            }

            @Override
            public Period fromString(String s) {
                return null;
            }
        });
    }

    private void populateTable() throws SQLException {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblemsWithoutSolution = academicProblemDAO.getAcademicProblemsWithSolutionByProgram(1);

        int idSolution = 0;
        int positionSolution = 0;
        for(AcademicProblem academicProblem : academicProblemsWithoutSolution) {
            if(academicProblem.getIdSolution() != idSolution) {
                SolutionsTable solutionsFromTable = new SolutionsTable();
                solutionsFromTable.setIdAcademicProblem(academicProblem.getIdAcademicProblem());
                solutionsFromTable.setTitle(academicProblem.getTitle());
                solutionsFromTable.setEe(academicProblem.getEe());
                solutionsFromTable.setTeacher(academicProblem.getTeacher());
                solutionsFromTable.setDescription(academicProblem.getDescription());
                solutionsFromTable.setPeriod(academicProblem.getPeriod());
                solutionsFromTable.setIdSolution(academicProblem.getIdSolution());
                solutionsFromTable.setSolution(academicProblem.getSolution());

                solutionsFromTable.getCbAcademicProblems().getItems().add(academicProblem.getTitle());
                solutionsFromTable.getCbAcademicProblems().getSelectionModel().selectFirst();

                idSolution = academicProblem.getIdSolution();
                positionSolution++;
                tableSolutions.add(solutionsFromTable);
            } else {
                SolutionsTable solution = tableSolutions.get(positionSolution-1);
                solution.getCbAcademicProblems().getItems().add(academicProblem.getTitle());
            }
        }

        this.tcAcademicProblem.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCbAcademicProblems()));
        this.tcTeacher.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTeacher()));
        this.tcEE.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEe()));
        this.tvAcademicProblems.setItems(tableSolutions);
    }

    private void seeSolutionListener() throws SQLException {
        this.tvAcademicProblems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                AcademicProblem selectedSolutionToAcademicProblem = this.tvAcademicProblems.getSelectionModel().getSelectedItem();
                String solution = selectedSolutionToAcademicProblem.getSolution();
                this.taSolution.setText(solution);
            }
        });
    }

    @FXML
    private void filterAcademicProblems(ActionEvent event) {
        Teacher selectedTeacher = (Teacher) this.cbTeacher.getSelectionModel().getSelectedItem();
        String selectedTeacherName = selectedTeacher.getFullName().replaceAll("\\s", "");
        EE selectedEE = (EE) this.cbEE.getSelectionModel().getSelectedItem();
        String selectedEEName = selectedEE.getName().replaceAll("\\s", "");
        Period selectedPeriod = (Period) this.cbPeriod.getSelectionModel().getSelectedItem();
        String selectedPeriodDates = selectedPeriod.getFullPeriod().replaceAll("\\s", "");

        ObservableList<SolutionsTable> filteredAcademicProblems = FXCollections.observableArrayList();

        if(!selectedTeacherName.isEmpty() && !selectedEEName.isEmpty() && !selectedPeriodDates.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getEe().equals(selectedEE.getName())
                        && academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedTeacherName.isEmpty() && !selectedPeriodDates.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedEEName.isEmpty() && !selectedPeriodDates.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getEe().equals(selectedEE.getName())
                        && academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedTeacherName.isEmpty() && !selectedEEName.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getEe().equals(selectedEE.getName()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedTeacherName.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedEEName.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getEe().equals(selectedEE.getName()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedPeriodDates.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else {
            filteredAcademicProblems = tableSolutions;
        }

        tvAcademicProblems.setItems(filteredAcademicProblems);
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickModify(ActionEvent event) {

    }

    @FXML
    private void clickDelete(ActionEvent event) {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        SolutionsTable selectedSolution = this.tvAcademicProblems.getSelectionModel().getSelectedItem();

        if(selectedSolution != null) {
            try {
                boolean deletedSolution = academicProblemDAO.deleteSolution(selectedSolution.getIdSolution());
                if(deletedSolution) {
                    this.tvAcademicProblems.getItems().remove(selectedSolution);
                    WindowManagement.showAlert("Solución eliminada",
                            "La solución ha sido eliminada exitosamente",
                            Alert.AlertType.CONFIRMATION);
                } else {
                    WindowManagement.showAlert("Solución no eliminada",
                            "La solución no ha sido eliminada",
                            Alert.AlertType.ERROR);
                }
            } catch (SQLException sqlException) {
                WindowManagement.connectionLostMessage();
            }
        } else {
            WindowManagement.showAlert("No se ha seleccionado una solución",
                    "Seleccione la solución a eliminar",
                    Alert.AlertType.WARNING);
        }
    }
}
