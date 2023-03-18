package com.teamfour.sistutorias.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.domain.*;


/**
 * FXML Controller class
 *
 * @author SILVERWOLF
 */
public class LoginController implements Initializable {

    @javafx.fxml.FXML
    private Label lblPassword;
    @javafx.fxml.FXML
    private PasswordField txtPassword;
    @javafx.fxml.FXML
    private Label lblUser;
    @javafx.fxml.FXML
    private TextField txtUser;
    @javafx.fxml.FXML
    private Button btnSignIn;
    @javafx.fxml.FXML
    private Label txtTitulo;
    @javafx.fxml.FXML
    private Button btnExit;
    @javafx.fxml.FXML
    private ImageView imgLogo;
    @javafx.fxml.FXML
    private Label lblInvalidUser;
    @javafx.fxml.FXML
    private Label lblInvalidPassword;
    Messages alerts = new Messages();
    @FXML
    private ComboBox cbTipeUser;
    @FXML
    private Label lblTipeUser;
    @FXML
    private Button btnLoadUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void validateUser(String uvAcount, String password) {
        ArrayList<UserRoleProgram> users;
        try {
            UserRoleProgramDAO userRoleProgram = new UserRoleProgramDAO();
            users = userRoleProgram.searchUser(uvAcount, password);
            if (users.size() == 1) {
                SessionGlobalData.getSessionGlobalData().setUserRoleProgram(users.get(0));
                invoqueWindow();
            } else if (users.size() > 1) {
                showSelectRol(users);
            } else {
                alerts.mostrarAlertaUsuarioIncorrecto();
            }
        }catch (SQLException exception) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
            alerts.mostrarAlertaErrorConexionDB();
        }
    }

    private void invoqueWindow() {
        int tipeUser = SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getIdRole();
        switch (tipeUser) {
            case 4:
                showMenu("AdminMenu.fxml", "Menu de administradores");
                break;
            case 3:
                showMenu("TutoringCoordinatorMenu.fxml", "Menu de Jefe de carrera");
                break;
            case 2:
                showMenu("TutoringCoordinatorMenu.fxml", "Menu de coordinadores");
                break;
            case 1:
                showMenu("TutorMenu.fxml", "Menu de tutores");
                break;
        }
    }

    private void showSelectRol(ArrayList<UserRoleProgram> users){
        ObservableList<UserRoleProgram> roles = FXCollections.observableArrayList();
        for(UserRoleProgram user : users){
            roles.add(user);
        }
        cbTipeUser.setItems(roles);

        cbTipeUser.valueProperty().addListener((ov, valorAntiguo, valorNuevo) -> {
            btnLoadUser.setDisable(false);
            SessionGlobalData.getSessionGlobalData().setUserRoleProgram((UserRoleProgram) valorNuevo);
        });

        lblPassword.setVisible(false);
        lblUser.setVisible(false);
        txtPassword.setVisible(false);
        txtUser.setVisible(false);
        btnSignIn.setVisible(false);
        btnLoadUser.setVisible(true);
        lblTipeUser.setVisible(true);
        cbTipeUser.setVisible(true);
    }

    private void showMenu(String windowResource, String title){
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResource(windowResource).openStream());
            Scene scene = new Scene(root);
            stageMenuTutor.setScene(scene);
            stageMenuTutor.setTitle(title);
            stageMenuTutor.alwaysOnTopProperty();
            stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
            stageMenuTutor.show();
            closeAux();
        } catch (IOException exception){
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
        }
    }

    @javafx.fxml.FXML
    private void signUp(ActionEvent event) {
        String uvAcount = txtUser.getText();
        String password = txtPassword.getText();
        if(uvAcount.isEmpty() || uvAcount.length() > 15){
            lblInvalidUser.setVisible(true);
        }else if(password.isEmpty() || password.length() > 15){
            lblInvalidPassword.setVisible(true);
        }else {
            lblInvalidUser.setVisible(false);
            lblInvalidPassword.setVisible(false);
            validateUser(uvAcount, password);
        }
    }

    @javafx.fxml.FXML
    private void close(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private void closeAux() {
        Stage stageActual = (Stage)lblUser.getScene().getWindow();
        Stage stage = (Stage) stageActual.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void lauchSpecific(ActionEvent actionEvent) {
        invoqueWindow();
    }
}
