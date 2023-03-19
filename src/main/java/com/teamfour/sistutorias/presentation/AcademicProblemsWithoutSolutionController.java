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

public class AcademicProblemsWithoutSolutionController implements Initializable {

    @FXML
    private ComboBox cbEE;
    @FXML
    private TableView<AcademicProblemsTable> tvAcademicProblems;
    @FXML
    private TableColumn<AcademicProblemsTable, CheckBox> tcCheckbox;
    @FXML
    private TableColumn<AcademicProblemsTable, String> tcAcademicProblem;
    @FXML
    private TableColumn<AcademicProblemsTable, String> tcTeacher;
    @FXML
    private TableColumn<AcademicProblemsTable, String> tcEE;
    @FXML
    private ComboBox cbTeacher;
    @FXML
    private ComboBox cbPeriod;
    @FXML
    private TextArea taAcademicProblem;
    @FXML
    private TextArea taSolution;

    final int MAX_CHARS = 100;
    private ObservableList<AcademicProblemsTable> tableAcademicProblems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBoxes();
            populateTable();
            seeAcademicProblemListener();
            taSolution.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= MAX_CHARS ? change : null));
        } catch (SQLException sqlException) {
            WindowManagement.connectionLostMessage();
            WindowManagement.closeWindow(new ActionEvent());
        }
    }

    public void populateComboBoxes() throws SQLException {
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

    public void populateTable() throws SQLException {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblemsWithoutSolution = academicProblemDAO.getAcademicProblemsWithoutSolutionByProgram(1);
        for(AcademicProblem academicProblem : academicProblemsWithoutSolution) {
            AcademicProblemsTable academicProblemFromTable = new AcademicProblemsTable();
            academicProblemFromTable.setIdAcademicProblem(academicProblem.getIdAcademicProblem());
            academicProblemFromTable.setTitle(academicProblem.getTitle());
            academicProblemFromTable.setEe(academicProblem.getEe());
            academicProblemFromTable.setTeacher(academicProblem.getTeacher());
            academicProblemFromTable.setDescription(academicProblem.getDescription());
            academicProblemFromTable.setPeriod(academicProblem.getPeriod());
            tableAcademicProblems.add(academicProblemFromTable);
        }

        this.tcCheckbox.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCheckBox()));
        this.tcAcademicProblem.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTitle()));
        this.tcTeacher.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTeacher()));
        this.tcEE.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEe()));
        this.tvAcademicProblems.setItems(tableAcademicProblems);
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickSave(ActionEvent event) {
        boolean emptySolution = this.taSolution.getText().isEmpty();

        ArrayList<AcademicProblemsTable> selectedAcademicProblems = new ArrayList<>();
        boolean noProblemsSelected = validateAProblemWasSelected(selectedAcademicProblems);

        int totalSelectedProblems = selectedAcademicProblems.size();
        boolean differentProblemsSelected = false;
        if(totalSelectedProblems > 1) {
            differentProblemsSelected = validateSameProblemWasSelected(selectedAcademicProblems);
        }

        if(emptySolution) {
            WindowManagement.showAlert("Solución no registrada",
                    "No se ha ingresado una solución",
                    Alert.AlertType.WARNING);
        } else if(noProblemsSelected) {
            WindowManagement.showAlert("Solución no registrada",
                    "No se seleccionó una problemática",
                    Alert.AlertType.WARNING);
        } else if(differentProblemsSelected) {
            WindowManagement.showAlert("Solución no registrada",
                    "Una o más de las problemáticas seleccionadas no coinciden con el docente y/o experiencia educativa",
                    Alert.AlertType.WARNING);
        } else {
            // SOUT START
            for(AcademicProblemsTable academicProblem : selectedAcademicProblems) {
                System.out.println(academicProblem.getIdAcademicProblem());
            }
            System.out.println(this.taSolution.getText());
            // SOUT END

            AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();

            try {
                int solution = academicProblemDAO.registerSolutionToAcademicProblem(this.taSolution.getText());
                if(solution != -1) {
                    for(AcademicProblemsTable academicProblem : selectedAcademicProblems) {

                    }
                    WindowManagement.showAlert("Solución registrada",
                            "La solución se registró correctamente",
                            Alert.AlertType.CONFIRMATION);
                } else {
                    WindowManagement.showAlert("Solución no registrada",
                            "La solución no ha sido registrada",
                            Alert.AlertType.WARNING);
                }
            } catch (SQLException sqlException) {
                WindowManagement.connectionLostMessage();
            }
        }
    }

    private boolean validateAProblemWasSelected(ArrayList<AcademicProblemsTable> selectedAcademicProblems) {
        ObservableList<AcademicProblemsTable> academicProblemsShown = this.tableAcademicProblems;
        boolean noProblemsSelected = true;
        for(AcademicProblemsTable academicProblem : academicProblemsShown) {
            if(academicProblem.getCheckBox().isSelected()) {
                selectedAcademicProblems.add(academicProblem);
                noProblemsSelected = false;
            }
        }
        return noProblemsSelected;
    }

    private boolean validateSameProblemWasSelected(ArrayList<AcademicProblemsTable> selectedAcademicProblems) {
        boolean differentProblemsSelected = false;
        int iterator = 0;
        String relatedTeacher = "";
        String relatedEE = "";
        for(AcademicProblemsTable academicProblem : selectedAcademicProblems) {
            if(iterator == 0) {
                relatedTeacher = academicProblem.getTeacher();
                relatedEE = academicProblem.getEe();
            } else if(!academicProblem.getTeacher().equals(relatedTeacher) || !academicProblem.getEe().equals(relatedEE)) {
                differentProblemsSelected = true;
            }
            iterator++;
        }
        return differentProblemsSelected;
    }

    @FXML
    private void filterAcademicProblems(ActionEvent event) {
        Teacher selectedTeacher = (Teacher) this.cbTeacher.getSelectionModel().getSelectedItem();
        String selectedTeacherName = selectedTeacher.getFullName().replaceAll("\\s", "");
        EE selectedEE = (EE) this.cbEE.getSelectionModel().getSelectedItem();
        String selectedEEName = selectedEE.getName().replaceAll("\\s", "");
        Period selectedPeriod = (Period) this.cbPeriod.getSelectionModel().getSelectedItem();
        String selectedPeriodDates = selectedPeriod.getFullPeriod().replaceAll("\\s", "");

        ObservableList<AcademicProblemsTable> filteredAcademicProblems = FXCollections.observableArrayList();

        if(!selectedTeacherName.isEmpty() && !selectedEEName.isEmpty() && !selectedPeriodDates.isEmpty()) {
            for(AcademicProblemsTable academicProblem : tableAcademicProblems) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getEe().equals(selectedEE.getName())
                        && academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedTeacherName.isEmpty() && !selectedPeriodDates.isEmpty()) {
            for(AcademicProblemsTable academicProblem : tableAcademicProblems) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedEEName.isEmpty() && !selectedPeriodDates.isEmpty()) {
            for(AcademicProblemsTable academicProblem : tableAcademicProblems) {
                if(academicProblem.getEe().equals(selectedEE.getName())
                        && academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedTeacherName.isEmpty() && !selectedEEName.isEmpty()) {
            for(AcademicProblemsTable academicProblem : tableAcademicProblems) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getEe().equals(selectedEE.getName()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedTeacherName.isEmpty()) {
            for(AcademicProblemsTable academicProblem : tableAcademicProblems) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedEEName.isEmpty()) {
            for(AcademicProblemsTable academicProblem : tableAcademicProblems) {
                if(academicProblem.getEe().equals(selectedEE.getName()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedPeriodDates.isEmpty()) {
            for(AcademicProblemsTable academicProblem : tableAcademicProblems) {
                if(academicProblem.getPeriod().getFullPeriod().equals(selectedPeriod.getFullPeriod()))
                    filteredAcademicProblems.add(academicProblem);
            }
        } else {
            filteredAcademicProblems = tableAcademicProblems;
        }

        tvAcademicProblems.setItems(filteredAcademicProblems);
    }

    private void seeAcademicProblemListener() {
        this.tvAcademicProblems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                AcademicProblemsTable selectedAcademicProblem = this.tvAcademicProblems.getSelectionModel().getSelectedItem();
                String problemDescription = selectedAcademicProblem.getDescription();
                this.taAcademicProblem.setText(problemDescription);
            }
        });
    }
}
