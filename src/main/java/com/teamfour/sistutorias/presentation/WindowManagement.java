package com.teamfour.sistutorias.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.net.URL;
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

    public static void changeScene(String title, URL resource) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.getIcons().add(new Image(WindowManagement.class.getResourceAsStream("images/Flor_uv.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
}
