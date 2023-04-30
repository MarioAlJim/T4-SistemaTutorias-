package com.teamfour.sistutorias.presentation;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.domain.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblUser;
    @FXML
    private TextField txtUser;
    @FXML
    private Label lblInvalidUser;
    @FXML
    private Label lblInvalidPassword;

    private boolean validateUser(String uvAcount) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return pattern.matcher(uvAcount).find();
    }

    private void invoqueWindow() {
        int tipeUser = 1;
        String window = "";
        String title = "";
        ArrayList<RoleProgram> roles = SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getRolesPrograms();
        for (RoleProgram roleProgram: roles) {
            if(roleProgram.getRole() == 4) {
                tipeUser = 2;
            }
        }

        switch (tipeUser) {
            case 2:
                window = "AdminMenu.fxml";
                title = "Menu de administrador";
                break;
            case 1:
                window = "MainMenu.fxml";
                title = "Menu principal";
                break;
        }

        Stage stage = new Stage();
        stage.setTitle(title);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(window));
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            closeAux();
            stage.show();
        } catch (IOException exception){
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
            WindowManagement.showAlert("Error", "Error no se pudo cargar el menu", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void signUp(ActionEvent event) {
        String uvAcount = txtUser.getText();
        String password = txtPassword.getText();
        if(uvAcount.isEmpty() || uvAcount.length() > 15){
            lblInvalidUser.setVisible(true);
        }else if(password.isEmpty() || password.length() > 15){
            lblInvalidPassword.setVisible(true);
        }else {
            if (validateUser(uvAcount)) {
                UserRoleProgram user;
                try {
                    UserRoleProgramDAO userRoleProgram = new UserRoleProgramDAO();
                    user = userRoleProgram.searchUser(uvAcount, password);
                    if (user.getEmail().equals(uvAcount)) {
                        SessionGlobalData.getSessionGlobalData().setUserRoleProgram(user);
                        invoqueWindow();
                    } else {
                        WindowManagement.showAlert("Error", "No se encontro el usuario, verifique la informacion", Alert.AlertType.INFORMATION);
                    }
                } catch (SQLException exception) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
                    WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
                }
            } else {
                WindowManagement.showAlert("Error", "El usuario ingresado no es valido", Alert.AlertType.INFORMATION);
            }
        }
    }

    @FXML
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
