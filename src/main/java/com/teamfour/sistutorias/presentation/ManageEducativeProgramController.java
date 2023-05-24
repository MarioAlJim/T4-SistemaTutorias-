package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EducativeProgramDAO;
import com.teamfour.sistutorias.domain.EducativeProgram;
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

public class ManageEducativeProgramController implements Initializable {
    @FXML
    private TextField tfSearchEducativeProgram;
    @FXML
    private TextField tfNameEducativeProgram;
    @FXML
    private TableView<EducativeProgram> tvEducativePrograms;
    @FXML
    private TableColumn<EducativeProgram, String> tcName;
    @FXML
    private Button btnModify;

    private final int MAX_CHARS = 100;
    private final ObservableList<EducativeProgram> educativePrograms = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateTable();
            searchEducativeProgram();
            seeSelectedEducativeProgram();
            this.tfSearchEducativeProgram.setTextFormatter(new TextFormatter<String>(change ->
                    change.getControlNewText().length() <= MAX_CHARS ? change : null));
            this.tfNameEducativeProgram.setTextFormatter(new TextFormatter<String>(change ->
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
        EducativeProgramDAO educativeProgramDAO = new EducativeProgramDAO();
        ArrayList<EducativeProgram> registeredEducativePrograms = educativeProgramDAO.getEducativePrograms();

        this.educativePrograms.addAll(registeredEducativePrograms);

        this.tcName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));
        this.tvEducativePrograms.setItems(educativePrograms);
    }

    private void searchEducativeProgram() {
        FilteredList<EducativeProgram> filteredEducativePrograms = new FilteredList<>(this.educativePrograms, b -> true);
        this.tfSearchEducativeProgram.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEducativePrograms.setPredicate(
                    educativeProgram -> {
                        if(newValue == null || newValue.isEmpty())
                            return true;

                        disableButtons(true);
                        this.tfNameEducativeProgram.clear();
                        String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");

                        return educativeProgram.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter);
                    }
            );
        });
        SortedList<EducativeProgram> educativeProgramSortedList = new SortedList<>(filteredEducativePrograms);
        educativeProgramSortedList.comparatorProperty().bind(this.tvEducativePrograms.comparatorProperty());
        this.tvEducativePrograms.setItems(educativeProgramSortedList);
    }

    private void seeSelectedEducativeProgram() {
        this.tvEducativePrograms.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldSelection, newSelection) -> {
            if(newSelection != null) {
                EducativeProgram selectedEducativeProgram = this.tvEducativePrograms.getSelectionModel().getSelectedItem();
                String educativeProgramName = selectedEducativeProgram.getName();
                this.tfNameEducativeProgram.setText(educativeProgramName);
                disableButtons(false);
            } else {
                this.tfNameEducativeProgram.clear();
            }
        }));
    }

    @FXML
    private void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    private void clickModify(ActionEvent event) {
        EducativeProgram selectedEducativeProgram = this.tvEducativePrograms.getSelectionModel().getSelectedItem();
        boolean emptyName = this.tfNameEducativeProgram.getText().replaceAll("\\s","").isEmpty();

        String newName = this.tfNameEducativeProgram.getText().trim().replaceAll(" +"," ");
        if(!emptyName) {
            try {
                EducativeProgramDAO educativeProgramDAO = new EducativeProgramDAO();
                boolean educativeProgramUpdated = educativeProgramDAO.updateEducativeProgram(new EducativeProgram(selectedEducativeProgram.getIdEducativeProgram(), newName));
                if(educativeProgramUpdated) {
                    WindowManagement.showAlert("Programa educativo actualizado",
                            "El programa educativo se ha actualizado exitosamente",
                            Alert.AlertType.INFORMATION);
                    selectedEducativeProgram.setName(newName);
                    this.tfNameEducativeProgram.clear();
                    this.tvEducativePrograms.refresh();
                    this.tvEducativePrograms.getSelectionModel().clearSelection();
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
        if (this.tfNameEducativeProgram.getText().isEmpty() || this.tfNameEducativeProgram.getText().isBlank()) {
            WindowManagement.showAlert("Programa educativo no registrado",
                    "No se ha ingresado un nombre de programa educativo",
                    Alert.AlertType.WARNING);
        } else {
            String educativeProgramName = this.tfNameEducativeProgram.getText().trim().replaceAll(" +"," ");

            boolean isAlreadyRegistered = false;

            for(EducativeProgram educativeProgram : this.educativePrograms) {
                if(educativeProgram.getName().equals(educativeProgramName)) {
                    isAlreadyRegistered = true;
                    break;
                }
            }

            if(isAlreadyRegistered) {
                WindowManagement.showAlert("Programa educativo no registrado",
                        "El programa educativo ha sido registrado anteriormente",
                        Alert.AlertType.WARNING);
            } else {
                EducativeProgramDAO educativeProgramDAO = new EducativeProgramDAO();
                try {
                    EducativeProgram educativeProgram = new EducativeProgram();
                    educativeProgram.setName(educativeProgramName);
                    int educativeProgramWasRegistered = educativeProgramDAO.register(educativeProgram);
                    if(educativeProgramWasRegistered != -1) {
                        educativeProgram.setIdEducativeProgram(educativeProgramWasRegistered);
                        this.educativePrograms.add(educativeProgram);
                        this.tfNameEducativeProgram.clear();
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
        this.tvEducativePrograms.getSelectionModel().clearSelection();
        disableButtons(true);
    }
}
