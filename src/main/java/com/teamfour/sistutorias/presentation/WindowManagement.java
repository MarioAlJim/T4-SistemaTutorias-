package com.teamfour.sistutorias.presentation;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.util.Optional;

public class WindowManagement {
    public static void showAlert(String title, String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showAlertWithConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> action = alert.showAndWait();
        return action.get() == ButtonType.OK;
    }

    public static void connectionLostMessage(){
        WindowManagement.showAlert(
                "Pérdida de conexión",
                "No se pudo conectar con la base de datos. Por favor, inténtelo más tarde.",
                Alert.AlertType.ERROR
        );
    }

    public static void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage scene = (Stage) source.getScene().getWindow();
        scene.close();
    }
}
