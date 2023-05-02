package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.AcademicProblemDAO;
import com.teamfour.sistutorias.bussinesslogic.EEDAO;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.domain.AcademicProblem;
import com.teamfour.sistutorias.domain.EE;
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

public class ModifySolutionToAcademicProblemController implements Initializable {

    @FXML
    private ComboBox<EE> cbEE;
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
    private ComboBox<Teacher> cbTeacher;
    @FXML
    private TextArea taAcademicProblem;
    @FXML
    private TextArea taSolution;

    final int MAX_CHARS = 100;
    private final ObservableList<AcademicProblemsTable> tableAcademicProblems = FXCollections.observableArrayList();
    private ArrayList<Integer> previouslySelectedAcademicProblems = new ArrayList<>();
    private int solution;

    public void setPreviouslySelectedAcademicProblems(ArrayList<Integer> previouslySelectedAcademicProblems) {
        this.previouslySelectedAcademicProblems = previouslySelectedAcademicProblems;
    }

    public void setSolution(int solution) {
        this.solution = solution;
    }

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
        teachers.addAll(teacherDAO.getTeachersByProgram(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram()));
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
        ees.addAll(eedao.getEEsByProgram(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram()));
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
    }

    public void populateTable() throws SQLException {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblemsWithoutSolution = academicProblemDAO.getAcademicProblemsWithoutSolutionByProgram(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram());

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

    public void addSelectedElements() throws SQLException {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();

        for(Integer selectedAcademicProblemId : this.previouslySelectedAcademicProblems) {
            AcademicProblem selectedAcademicProblem = academicProblemDAO.getAcademicProblemById(selectedAcademicProblemId.intValue());
            AcademicProblemsTable academicProblemFromTable = new AcademicProblemsTable();
            academicProblemFromTable.setIdAcademicProblem(selectedAcademicProblem.getIdAcademicProblem());
            academicProblemFromTable.setTitle(selectedAcademicProblem.getTitle());
            academicProblemFromTable.setEe(selectedAcademicProblem.getEe());
            academicProblemFromTable.setTeacher(selectedAcademicProblem.getTeacher());
            academicProblemFromTable.setDescription(selectedAcademicProblem.getDescription());
            academicProblemFromTable.setPeriod(selectedAcademicProblem.getPeriod());
            academicProblemFromTable.getCheckBox().setSelected(true);
            tableAcademicProblems.add(0,academicProblemFromTable);
        }
        String solution = academicProblemDAO.getSolutionById(this.solution);
        this.taSolution.setText(solution);
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickSave(ActionEvent event) {
        boolean emptySolution = this.taSolution.getText().replaceAll("\\s", "").isEmpty();
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
            AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
            try {
                int solutionUpdated = academicProblemDAO.updateSolution(this.solution, this.taSolution.getText());
                boolean solutionLinked = false;
                if(solutionUpdated != 0) {
                    for(Integer previouslySelectedAcademicProblem : this.previouslySelectedAcademicProblems) {
                        solutionLinked = academicProblemDAO.unlinkSolutionToProblems(previouslySelectedAcademicProblem.intValue(), this.solution);
                    }

                    for(AcademicProblemsTable newAcademicProblemSelected : selectedAcademicProblems) {
                        solutionLinked = academicProblemDAO.linkSolutionToProblems(newAcademicProblemSelected,this.solution);
                    }
                }

                if(solutionLinked) {
                    WindowManagement.showAlert("Solución registrada",
                            "La solución se registró correctamente",
                            Alert.AlertType.INFORMATION);
                    WindowManagement.closeWindow(event);
                } else {
                    WindowManagement.showAlert("Solución no registrada",
                            "La solución no ha sido registrada",
                            Alert.AlertType.ERROR);
                    WindowManagement.closeWindow(event);
                }

            } catch (SQLException sqlException) {
                WindowManagement.connectionLostMessage();
            }
        }
    }

    private boolean validateAProblemWasSelected(ArrayList<AcademicProblemsTable> selectedAcademicProblems) {
        boolean noProblemsSelected = true;
        for(AcademicProblemsTable academicProblem : this.tableAcademicProblems) {
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
