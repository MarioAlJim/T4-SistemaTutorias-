package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.domain.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
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
    private TableColumn<SolutionsTable, ComboBox<String>> tcAcademicProblem;
    @FXML
    private TableColumn<SolutionsTable, String> tcTeacher;
    @FXML
    private TableColumn<SolutionsTable, String> tcEE;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    private ArrayList<Group> groups = new ArrayList<>();
    private final ObservableList<SolutionsTable> tableSolutions = FXCollections.observableArrayList();
    private final ObservableList<Period> periods = FXCollections.observableArrayList();
    private final ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private final ObservableList<EE> ees = FXCollections.observableArrayList();
    private Period currentPeriod;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBoxes();
            populateTable();
            seeSolutionListener();
            filterAcademicProblems(new ActionEvent());
            disableButtons(true);
        } catch (SQLException sqlException) {
            WindowManagement.connectionLostMessage();
        }
    }

    private void disableButtons(boolean isDisabled) {
        this.btnModify.setDisable(isDisabled);
        this.btnDelete.setDisable(isDisabled);
    }

    private void populateComboBoxes() throws SQLException {
        PeriodDAO periodDAO = new PeriodDAO();

        periods.addAll(periodDAO.getPeriods());

        this.currentPeriod = periodDAO.getCurrentPeriod();

        this.cbPeriod.setItems(periods);
        this.cbPeriod.getSelectionModel().select(currentPeriod);

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

        GroupDAO groupDAO = new GroupDAO();
        this.groups = groupDAO.getGroupsByEducationProgram(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram());

        this.teachers.add(new Teacher());
        this.ees.add(new EE());

        for(Group group : this.groups) {
            if(group.getIdPeriod() == this.cbPeriod.getSelectionModel().getSelectedItem().getIdPeriod()) {
                this.teachers.add(group.getTeacher());
                this.ees.add(group.getEe());
            }
        }

        this.cbTeacher.setItems(this.teachers);
        this.cbEE.setItems(this.ees);
        this.cbTeacher.getSelectionModel().selectFirst();
        this.cbEE.getSelectionModel().selectFirst();

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

    private void populateTable() throws SQLException {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        ArrayList<AcademicProblem> academicProblemsWithSolution = academicProblemDAO.getAcademicProblemsWithSolutionByProgram(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducationProgram());

        int idSolution = 0;
        int positionSolution = 0;
        for(AcademicProblem academicProblem : academicProblemsWithSolution) {
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

                solutionsFromTable.addRelatedAcademicProblems(academicProblem.getIdAcademicProblem());

                idSolution = academicProblem.getIdSolution();
                positionSolution++;
                this.tableSolutions.add(solutionsFromTable);
            } else {
                SolutionsTable solution = this.tableSolutions.get(positionSolution-1);
                solution.getCbAcademicProblems().getItems().add(academicProblem.getTitle());
                solution.addRelatedAcademicProblems(academicProblem.getIdAcademicProblem());
            }
        }

        this.tcAcademicProblem.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCbAcademicProblems()));
        this.tcTeacher.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTeacher()));
        this.tcEE.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEe()));
    }

    private void seeSolutionListener() throws SQLException {
        this.tvAcademicProblems.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                AcademicProblem selectedSolutionToAcademicProblem = this.tvAcademicProblems.getSelectionModel().getSelectedItem();
                String solution = selectedSolutionToAcademicProblem.getSolution();
                this.taSolution.setText(solution);
                if(selectedSolutionToAcademicProblem.getPeriod().getIdPeriod() == currentPeriod.getIdPeriod()) {
                    disableButtons(false);
                }
            }
        });
    }

    @FXML
    private void filterAcademicProblems(ActionEvent event) {
        Teacher selectedTeacher = this.cbTeacher.getSelectionModel().getSelectedItem();
        String selectedTeacherName = selectedTeacher.getFullName().replaceAll("\\s", "");
        EE selectedEE = this.cbEE.getSelectionModel().getSelectedItem();
        String selectedEEName = selectedEE.getName().replaceAll("\\s", "");
        Period selectedPeriod = this.cbPeriod.getSelectionModel().getSelectedItem();

        ObservableList<SolutionsTable> filteredAcademicProblems = FXCollections.observableArrayList();

        if(!selectedTeacherName.isEmpty() && !selectedEEName.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getEe().equals(selectedEE.getName())
                        && academicProblem.getPeriod().getIdPeriod() == selectedPeriod.getIdPeriod())
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedTeacherName.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getTeacher().equals(selectedTeacher.getFullName())
                        && academicProblem.getPeriod().getIdPeriod() == selectedPeriod.getIdPeriod())
                    filteredAcademicProblems.add(academicProblem);
            }
        } else if(!selectedEEName.isEmpty()) {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getEe().equals(selectedEE.getName())
                        && academicProblem.getPeriod().getIdPeriod() == selectedPeriod.getIdPeriod())
                    filteredAcademicProblems.add(academicProblem);
            }
        } else {
            for(SolutionsTable academicProblem : tableSolutions) {
                if(academicProblem.getPeriod().getIdPeriod() == selectedPeriod.getIdPeriod())
                    filteredAcademicProblems.add(academicProblem);
            }
        }

        this.tvAcademicProblems.setItems(filteredAcademicProblems);

        if(this.tvAcademicProblems.getSelectionModel().getSelectedItem() == null) {
            disableButtons(true);
            this.taSolution.clear();
        }
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickModify(ActionEvent event) throws IOException {
        SolutionsTable selectedSolution = this.tvAcademicProblems.getSelectionModel().getSelectedItem();

        Stage stage = new Stage();
        stage.setTitle("Modificar solución a problemática académica");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifySolutionToAcademicProblem.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            ModifySolutionToAcademicProblemController modifySolutionController = loader.getController();
            modifySolutionController.setPreviouslySelectedAcademicProblems(selectedSolution.getRelatedAcademicProblems());
            modifySolutionController.setSolution(selectedSolution.getIdSolution());
            modifySolutionController.addSelectedElements();
            stage.getIcons().add(new Image(WindowManagement.class.getResourceAsStream("images/Flor_uv.png")));
            stage.showAndWait();

            //Reload data
            this.tableSolutions.clear();
            this.taSolution.clear();
            populateTable();
            disableButtons(true);
        } catch (SQLException sqlException) {
            WindowManagement.connectionLostMessage();
            WindowManagement.closeWindow(event);
        }
    }

    @FXML
    private void clickDelete(ActionEvent event) {
        AcademicProblemDAO academicProblemDAO = new AcademicProblemDAO();
        SolutionsTable selectedSolution = this.tvAcademicProblems.getSelectionModel().getSelectedItem();

        boolean deleteSolution = false;
        deleteSolution = WindowManagement.showAlertWithConfirmation("Eliminar solución", "¿Desea eliminar la solución seleccionada?");
        if(deleteSolution) {
            try {
                boolean deletedSolution = academicProblemDAO.deleteSolution(selectedSolution.getIdSolution());
                if(deletedSolution) {
                    this.taSolution.clear();
                    this.tvAcademicProblems.getItems().remove(selectedSolution);
                    WindowManagement.showAlert("Solución eliminada",
                            "La solución ha sido eliminada exitosamente",
                            Alert.AlertType.INFORMATION);
                    disableButtons(true);
                } else {
                    WindowManagement.showAlert("Solución no eliminada",
                            "La solución no ha sido eliminada",
                            Alert.AlertType.ERROR);
                }
            } catch (SQLException sqlException) {
                WindowManagement.connectionLostMessage();
            }
        }
    }
}
