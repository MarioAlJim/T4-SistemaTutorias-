package com.teamfour.sistutorias.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(Locale.Category.FORMAT, new Locale("es", "US"));
        //scene = new Scene(loadFXML("ConsultAcademicProblems"));
        //scene = new Scene(loadFXML("Login"));
        //scene = new Scene(loadFXML("Tutorship"));
        //scene = new Scene(loadFXML("ModifyAsignmentTutorTutorado"));
        //scene = new Scene(loadFXML("RegisterProblem"));
        //scene = new Scene(loadFXML("AdminMenu"));
        //scene = new Scene(loadFXML("ConsultAcademicProblems"));
        //scene = new Scene(loadFXML("Login"));
        //scene = new Scene(loadFXML("TeachersAdministration"));
        scene = new Scene(loadFXML("GroupAdministration"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}