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
<<<<<<< HEAD
        //scene = new Scene(loadFXML("ConsultAcademicProblems"));
        //scene = new Scene(loadFXML("Login"));
        scene = new Scene(loadFXML("ModifyAsignmentTutorTutorado"));
=======
        //scene = new Scene(loadFXML("Tutorship"));
        //scene = new Scene(loadFXML("ModifyAsignmentTutorTutorado"));
        //scene = new Scene(loadFXML("RegisterProblem"));
        //scene = new Scene(loadFXML("AdminMenu"));
<<<<<<< HEAD
>>>>>>> 5b27d1f5f2da5a91595b9505067e2c628c5c021c
=======
        //scene = new Scene(loadFXML("ConsultAcademicProblems"));
        //scene = new Scene(loadFXML("Login"));
        scene = new Scene(loadFXML("ManageEducationProgram"));
        //scene = new Scene(loadFXML("RegisterSolutionToAcademicProblem"));
>>>>>>> 213c881bd53062db1e1b68ce592fddcda840a334
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