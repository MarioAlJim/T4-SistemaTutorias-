/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.teamfour.sistutorias.presentation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.teamfour.sistutorias.dataaccess.DataBaseConnection;
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

    private ArrayList<UserRoleProgram> users = new ArrayList<UserRoleProgram>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @javafx.fxml.FXML
    private void signUp(ActionEvent event) {
        String uvAcount = txtUser.getText();
        String password = txtPassword.getText();
        if(uvAcount.isEmpty()){
            lblInvalidUser.setVisible(true);
        }else if(password.isEmpty()){
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

    private void closeAux(ActionEvent event) {
        Stage stageActual = (Stage)lblUser.getScene().getWindow();
        Stage stage = (Stage) stageActual.getScene().getWindow();
        stage.close();
    }

    private void validateUser(String uvAcount, String password) {
        Messages alerts = new Messages();
       /*ry {
           // RoleDAO roleDAO = new RoleDAO();
            //users = RoleDAO.searchUser(uvAcount, password);
            if (users.size() == 1) {

            } else if (users.size() > 1) {
                switch(usuarioRecuperado.getRol()) {
                    case 1:
                        //mostrarMenuAdministrador();
                        break;
                    case 2:
                        //mostrarMenuJefedeCarrera(usuarioRecuperado);
                        break;
                    case 3:
                        //mostrarMenuCoordinaro(usuarioRecuperado);
                        break;
                    case 4:
                        //mostrarMenuTutor(usuarioRecuperado);
                        break;
                    default:
                        alerts.mostrarAlertaUsuarioIncorrecto();
                        break;
                }
            } else {
                alerts.mostrarAlertaUsuarioIncorrecto();
            }
        }catch (SQLException exception) {
            Logger.getLogger(DataBaseConnection.class.getName()).log(Level.SEVERE, null, exception);
            alerts.mostrarAlertaErrorConexionDB();
        }*/
    }

    /*private void mostrarMenuTutor(Usuario usuarioRecuperado) throws IOException{
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/uv/gui/interfaces/MenuTutorGUI.fxml").openStream());
        MenuTutorGUIController menuTutorGUIController = (MenuTutorGUIController) loader.getController();
        menuTutorGUIController.recibirParametros(usuarioRecuperado);
        Scene scene = new Scene(root);
        stageMenuTutor.setScene(scene);
        stageMenuTutor.setTitle("Menu de tutores");
        stageMenuTutor.alwaysOnTopProperty();
        stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
        stageMenuTutor.show();
        cerrarVentanaAux();
    }

    private void mostrarMenuAdministrador() throws IOException{
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/uv/gui/interfaces/MenuAdministradorGUI.fxml").openStream());
        Scene scene = new Scene(root);
        stageMenuTutor.setScene(scene);
        stageMenuTutor.setTitle("Menu de administradores");
        stageMenuTutor.alwaysOnTopProperty();
        stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
        stageMenuTutor.show();
        cerrarVentanaAux();
    }

    private void mostrarMenuCoordinaro(Usuario usuarioRecuperado) throws IOException{
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/uv/gui/interfaces/MenuCoordinadorGUI.fxml").openStream());
        MenuCoordinadorGUIController menuCoordinadorGUIController = (MenuCoordinadorGUIController) loader.getController();
        menuCoordinadorGUIController.recibirParametros(usuarioRecuperado);
        Scene scene = new Scene(root);
        stageMenuTutor.setScene(scene);
        stageMenuTutor.setTitle("Menu de coordinadores");
        stageMenuTutor.alwaysOnTopProperty();
        stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
        stageMenuTutor.show();
    }

    private void mostrarMenuJefedeCarrera(Usuario usuarioRecuperado) throws IOException {
        Stage stageMenuTutor = new Stage();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("/uv/gui/interfaces/MenuJefedeCarreraGUI.fxml").openStream());
        MenuJefedeCarreraGUIController menuJefedeCarreraGUIController = (MenuJefedeCarreraGUIController) loader.getController();
        menuJefedeCarreraGUIController.recibirParametros(usuarioRecuperado);
        Scene scene = new Scene(root);
        stageMenuTutor.setScene(scene);
        stageMenuTutor.setTitle("Menu de Jefe de carrera");
        stageMenuTutor.alwaysOnTopProperty();
        stageMenuTutor.initModality(Modality.APPLICATION_MODAL);
        stageMenuTutor.show();
        cerrarVentanaAux();
    }*/

}
