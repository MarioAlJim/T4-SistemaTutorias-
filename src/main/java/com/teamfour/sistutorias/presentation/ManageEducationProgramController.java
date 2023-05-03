package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EducationProgramDAO;
import com.teamfour.sistutorias.domain.EducationProgram;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageEducationProgramController implements Initializable {
    @FXML
    private TextField tfSearchEducationProgram;
    @FXML
    private TextField tfNameEducationProgram;
    @FXML
    private TableView<EducationProgram> tvEducationPrograms;
    @FXML
    private TableColumn<EducationProgram, String> tcName;
    @FXML
    private Button btnModify;

    private final int MAX_CHARS = 100;
    private final ObservableList<EducationProgram> educationPrograms = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTable();
            searchEducationProgram();
            seeSelectedEducationProgram();
            this.tfSearchEducationProgram.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= MAX_CHARS ? change : null));
            this.tfNameEducationProgram.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= MAX_CHARS ? change : null));
            disableButtons(true);
        } catch(SQLException sqlException) {
            WindowManagement.connectionLostMessage();
            WindowManagement.closeWindow(new ActionEvent());
        }
    }

    private void disableButtons(boolean isDisabled) {
        this.btnModify.setDisable(isDisabled);
    }

    private void populateTable() throws SQLException {
        EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
        ArrayList<EducationProgram> registeredEducationPrograms = educationProgramDAO.getEducationPrograms();

        this.educationPrograms.addAll(registeredEducationPrograms);

        this.tcName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        this.tvEducationPrograms.setItems(educationPrograms);
    }

    private void searchEducationProgram() {
        FilteredList<EducationProgram> filteredEducationPrograms = new FilteredList<>(this.educationPrograms, b -> true);
        this.tfSearchEducationProgram.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEducationPrograms.setPredicate(
                    educationProgram -> {
                        if(newValue == null || newValue.isEmpty())
                            return true;

                        disableButtons(true);
                        this.tfNameEducationProgram.clear();
                        String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");

                        return educationProgram.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter);
                    }
            );
        });
        SortedList<EducationProgram> educationProgramSortedList = new SortedList<>(filteredEducationPrograms);
        educationProgramSortedList.comparatorProperty().bind(this.tvEducationPrograms.comparatorProperty());
        this.tvEducationPrograms.setItems(educationProgramSortedList);
    }

    private void seeSelectedEducationProgram() {
        this.tvEducationPrograms.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldSelection, newSelection) -> {
            if(newSelection != null) {
                EducationProgram selectedEducationProgram = this.tvEducationPrograms.getSelectionModel().getSelectedItem();
                String educationProgramName = selectedEducationProgram.getName();
                this.tfNameEducationProgram.setText(educationProgramName);
                disableButtons(false);
            } else {
                this.tfNameEducationProgram.clear();
            }
        }));
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickModify(ActionEvent event) {
        EducationProgram selectedEducationProgram = this.tvEducationPrograms.getSelectionModel().getSelectedItem();
        boolean emptyName = this.tfNameEducationProgram.getText().replaceAll("\\s","").isEmpty();

        String newName = this.tfNameEducationProgram.getText().trim().replaceAll(" +"," ");
        if(!emptyName) {
            try {
                EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
                boolean educationProgramUpdated = educationProgramDAO.updateEducationProgram(new EducationProgram(selectedEducationProgram.getIdEducationProgram(), newName));
                if(educationProgramUpdated) {
                    WindowManagement.showAlert("Programa educativo actualizado",
                            "El programa educativo se ha actualizado exitosamente",
                            Alert.AlertType.INFORMATION);
                    selectedEducationProgram.setName(newName);
                    this.tfNameEducationProgram.clear();
                    this.tvEducationPrograms.refresh();
                    this.tvEducationPrograms.getSelectionModel().clearSelection();
                    disableButtons(true);
                } else {
                    WindowManagement.showAlert("Programa educativo no actualizado",
                            "El programa educativo no ha sido actualizado",
                            Alert.AlertType.ERROR);
                }
            } catch(SQLException sqlException) {
                WindowManagement.connectionLostMessage();
                WindowManagement.closeWindow(event);
            }
        } else {
            WindowManagement.showAlert("Campos vacíos",
                    "No se ha ingresado un nombre de programa educativo",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clickRegister(ActionEvent event) {
        if (this.tfNameEducationProgram.getText().isEmpty() || this.tfNameEducationProgram.getText().isBlank()) {
            WindowManagement.showAlert("Programa educativo no registrado",
                    "No se ha ingresado un nombre de programa educativo",
                    Alert.AlertType.WARNING);
        } else {
            String educationProgramName = this.tfNameEducationProgram.getText().trim().replaceAll(" +"," ");

            boolean isAlreadyRegistered = false;

            for(EducationProgram educationProgram : this.educationPrograms) {
                if(educationProgram.getName().equals(educationProgramName)) {
                    isAlreadyRegistered = true;
                    break;
                }
            }

            if(isAlreadyRegistered) {
                WindowManagement.showAlert("Programa educativo no registrado",
                        "El programa educativo ha sido registrado anteriormente",
                        Alert.AlertType.WARNING);
            } else {
                EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
                try {
                    EducationProgram educationProgram = new EducationProgram();
                    educationProgram.setName(educationProgramName);
                    int educationProgramWasRegistered = educationProgramDAO.register(educationProgram);
                    if(educationProgramWasRegistered != -1) {
                        educationProgram.setIdEducationProgram(educationProgramWasRegistered);
                        this.educationPrograms.add(educationProgram);
                        this.tfNameEducationProgram.clear();
                        WindowManagement.showAlert("Programa educativo registrado exitosamente",
                                "El programa educativo ha sido registrado exitosamente",
                                Alert.AlertType.INFORMATION);
                    } else {
                        WindowManagement.showAlert("Programa educativo no registrado",
                                "El programa educativo no se ha registrado",
                                Alert.AlertType.ERROR);
                    }
                } catch (SQLException sqlException) {
                    WindowManagement.connectionLostMessage();
                }
            }
        }
        this.tvEducationPrograms.getSelectionModel().clearSelection();
        disableButtons(true);
    }
}
