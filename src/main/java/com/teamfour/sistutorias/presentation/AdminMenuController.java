package com.teamfour.sistutorias.presentation;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void clickManageUsers() throws IOException {
        WindowManagement.changeScene("Administrar usuarios",
                getClass().getResource(""));
    }

    public void clickManageEducationProgram() throws IOException {
        WindowManagement.changeScene("Administrar programas educativos",
                getClass().getResource("ManageEducationProgram.fxml"));
    }

    public void clickManageTeacher() throws IOException {
        WindowManagement.changeScene("Administrar docentes",
                getClass().getResource("TeachersAdministration.fxml"));
    }

    public void clickManageEE() throws IOException {
        WindowManagement.changeScene("Administrar EEs",
                getClass().getResource(""));
    }

    public void clickManageGroups() throws IOException {
        WindowManagement.changeScene("Administrar grupos",
                getClass().getResource("GroupAdministration.fxml"));
    }
}
