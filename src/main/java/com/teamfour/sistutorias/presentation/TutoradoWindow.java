package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EducationProgramDAO;
import com.teamfour.sistutorias.bussinesslogic.TutoradoDAO;
import com.teamfour.sistutorias.domain.EducativeProgram;
import com.teamfour.sistutorias.domain.Tutorado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TutoradoWindow implements Initializable {

    @FXML
    private ComboBox<String> cbEducativeProgram;

    @FXML
    private TextField tfMaternalSurname;

    @FXML
    private TextField tfMatricula;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPaternalSurname;

    private Tutorado tutorado;
    private boolean isEditing;
    private ObservableList<EducativeProgram> educativePrograms = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEducativePrograms();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) tfMatricula.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveChanges(ActionEvent event) {
        if (checkFields()) {
            tutorado.setName(tfName.getText());
            tutorado.setPaternalSurname(tfPaternalSurname.getText());
            tutorado.setMaternalSurname(tfMaternalSurname.getText());
            tutorado.setRegistrationNumber(tfMatricula.getText());

            for(EducativeProgram educativeProgram : this.educativePrograms) {
                if(educativeProgram.getName().equals(this.cbEducativeProgram.getSelectionModel().getSelectedItem())) {
                    tutorado.setProgramId(educativeProgram.getIdEducationProgram());
                    break;
                }
            }

            if (isEditing) {
                updateTutorado();
            } else {
                registerTutorado();
            }
        }
    }

    private void registerTutorado() {
        TutoradoDAO tutoradoDAO = new TutoradoDAO();
        try {
            tutoradoDAO.register(tutorado);
            WindowManagement.showAlert("Éxito", "Tutorado registrado correctamente", Alert.AlertType.INFORMATION);
            closeWindow(null);
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "No se pudo registrar el tutorado", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void updateTutorado() {
        TutoradoDAO tutoradoDAO = new TutoradoDAO();
        try {
            tutoradoDAO.update(tutorado);
            WindowManagement.showAlert("Éxito", "Tutorado actualizado correctamente", Alert.AlertType.INFORMATION);
            closeWindow(null);
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "No se pudo actualizar el tutorado", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private boolean checkFields() {
        boolean isValid = true;
        if (tfMatricula.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "La matrícula no puede estar vacía", Alert.AlertType.ERROR);
            isValid = false;
        } else if (tfName.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "El nombre no puede estar vacío", Alert.AlertType.ERROR);
            isValid = false;
        } else if (tfPaternalSurname.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "El apellido paterno no puede estar vacío", Alert.AlertType.ERROR);
            isValid = false;
        } else if (tfMaternalSurname.getText().isEmpty()) {
            WindowManagement.showAlert("Error", "El apellido materno no puede estar vacío", Alert.AlertType.ERROR);
            isValid = false;
        } else if (cbEducativeProgram.getSelectionModel().isEmpty()) {
            WindowManagement.showAlert("Error", "Debe seleccionar un programa educativo", Alert.AlertType.ERROR);
            isValid = false;
        }
        return isValid;
    }

    private void loadEducativePrograms() {
        EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
        try {
            educativePrograms.addAll(educationProgramDAO.getEducationPrograms());
            for (EducativeProgram educativeProgram : educativePrograms) {
                cbEducativeProgram.getItems().add(educativeProgram.getName());
            }
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "Error al cargar los programas educativos", Alert.AlertType.ERROR);
            closeWindow(null);
        }
    }

    public void setTutorado(Tutorado tutorado) {
        this.tutorado = tutorado;
    }

    public void setEdit(boolean editing) {
        isEditing = editing;
    }

    public void loadTutorado() {
        if (isEditing && tutorado != null) {
            tfMatricula.setText(tutorado.getRegistrationNumber());
            tfMatricula.setDisable(true);
            tfName.setText(tutorado.getName());
            tfPaternalSurname.setText(tutorado.getPaternalSurname());
            tfMaternalSurname.setText(tutorado.getMaternalSurname());
            cbEducativeProgram.setValue(tutorado.getEducativeProgram());
        } else {
            tutorado = new Tutorado();
        }
    }
}
