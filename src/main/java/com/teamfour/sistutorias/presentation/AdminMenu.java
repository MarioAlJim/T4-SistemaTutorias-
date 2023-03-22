package com.teamfour.sistutorias.presentation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMenu implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void clickRegisterEducationProgram() throws IOException {
        WindowManagement.changeScene("Programa educativo",
                getClass().getResource("EducationProgram.fxml"));
    }
}
