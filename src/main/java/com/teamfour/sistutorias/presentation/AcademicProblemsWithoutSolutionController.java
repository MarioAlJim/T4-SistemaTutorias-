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
import java.util.List;
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

    private ObservableList<AcademicProblemsTable> tableAcademicProblems = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBoxes();
            populateTable();
            seeAcademicProblemListener();
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
        /* TODO:
        //Crear metodo para traer solo los que no tienen solucion
        */
        List<AcademicProblem> academicProblemsWithoutSolution = academicProblemDAO.consultAcademicProblemsByProgram(1);
        for(AcademicProblem academicProblem : academicProblemsWithoutSolution) {
            AcademicProblemsTable academicProblemFromTable = new AcademicProblemsTable();
            academicProblemFromTable.setTitle(academicProblem.getTitle());
            academicProblemFromTable.setEe(academicProblem.getEe());
            academicProblemFromTable.setTeacher(academicProblem.getTeacher());
            tableAcademicProblems.add(academicProblemFromTable);
        }

        // TEMPORAL DATA
        AcademicProblemsTable academicProblem1 = new AcademicProblemsTable();
        academicProblem1.setTitle("Problem 1");
        academicProblem1.setIdAcademicProblem(1);
        academicProblem1.setTeacher("Joshua Hernandez Suarez");
        academicProblem1.setEe("Redes");
        academicProblem1.setDescription("Solution 1");
        tableAcademicProblems.add(academicProblem1);
        AcademicProblemsTable academicProblem2 = new AcademicProblemsTable();
        academicProblem2.setTitle("Problem 2");
        academicProblem2.setIdAcademicProblem(2);
        academicProblem2.setTeacher("Miriam Gomez Lopez");
        academicProblem2.setEe("Diseño de software");
        academicProblem2.setDescription("Solution 2");
        tableAcademicProblems.add(academicProblem2);
        AcademicProblemsTable academicProblem3 = new AcademicProblemsTable();
        academicProblem3.setTitle("Problem 3");
        academicProblem3.setIdAcademicProblem(3);
        academicProblem3.setTeacher("Joshua Hernandez Suarez");
        academicProblem3.setEe("Redes");
        academicProblem3.setDescription("Solution 3");
        tableAcademicProblems.add(academicProblem3);
        AcademicProblemsTable academicProblem4 = new AcademicProblemsTable();
        academicProblem4.setTitle("Problem 4");
        academicProblem4.setIdAcademicProblem(4);
        academicProblem4.setTeacher("Joshua Hernandez Suarez");
        academicProblem4.setEe("Diseño de software");
        academicProblem4.setDescription("Solution 4");
        tableAcademicProblems.add(academicProblem4);
        // END OF TEMPORAL DATA

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

        ObservableList<AcademicProblemsTable> filteredAcademicProblems = FXCollections.observableArrayList();

        if(!selectedTeacherName.isEmpty() && !selectedEEName.isEmpty()) {
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
