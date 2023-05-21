package com.teamfour.sistutorias.presentation;

import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private boolean activePeriod () {
        if (SessionGlobalData.getSessionGlobalData().getCurrentPeriod().getIdPeriod() != 0) {
            return true;
        } else {
            WindowManagement.showAlert("No disponible", "La accion no se puede realizar debido a que no hay periodos activos", Alert.AlertType.INFORMATION);
            return false;
        }
    }

    public void clickManageUsers() throws IOException {
        WindowManagement.changeScene("Administrar usuarios",
                getClass().getResource("ManageUsers.fxml"));
    }

    public void clickManageEducationProgram() throws IOException {
        WindowManagement.changeScene("Administrar programas educativos",
                getClass().getResource("ManageEducativeProgram.fxml"));
    }

    public void clickManageTeacher() throws IOException {
        WindowManagement.changeScene("Administrar docentes",
                getClass().getResource("TeachersAdministration.fxml"));
    }

    public void clickManageEE() throws IOException {
        WindowManagement.changeScene("Administrar EEs",
                getClass().getResource("EEAdministration.fxml"));
    }

    public void clickManageGroups() throws IOException {
        WindowManagement.changeScene("Administrar grupos",
                getClass().getResource("GroupAdministration.fxml"));
    }

    public void clickManageTutorship() throws IOException {
        if (activePeriod()) {
            WindowManagement.changeScene("Registrar sesión de tutoría académica",
                    getClass().getResource("Tutorship.fxml"));
        }
    }
}
