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
        WindowManagement.changeScene("",
                getClass().getResource(""));
    }

    public void clickManageEducationProgram() throws IOException {
        WindowManagement.changeScene("Administrar programa educativo",
                getClass().getResource("ManageEducationProgram.fxml"));
    }

    public void clickManageTeacher() throws IOException {
        WindowManagement.changeScene("",
                getClass().getResource(""));
    }

    public void clickManageEE() throws IOException {
        WindowManagement.changeScene("",
                getClass().getResource(""));
    }

    public void clickManageGroups() throws IOException {
        WindowManagement.changeScene("",
                getClass().getResource(""));
    }
}
