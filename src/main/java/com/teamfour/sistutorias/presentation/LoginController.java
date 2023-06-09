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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.teamfour.sistutorias.bussinesslogic.*;
import com.teamfour.sistutorias.domain.*;
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
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnExit;

    private boolean validateUser(String uvAcount) {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return pattern.matcher(uvAcount).find();
    }

    private void invoqueWindow() {
        int typeUser = 1;
        ArrayList<RoleProgram> roles = SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getRolesPrograms();
        for (RoleProgram roleProgram : roles) {
            if (roleProgram.getRole() == 4) {
                typeUser = 2;
            }
        }
        closeAux();
        PeriodDAO periodDAO = new PeriodDAO();
        TutorshipDAO tutorshipDAO = new TutorshipDAO();
        try {
            Period period = periodDAO.getCurrentPeriod();
            Tutorship tutorship = tutorshipDAO.getCurrentTutorship(period.getIdPeriod());
            SessionGlobalData.getSessionGlobalData().setCurrentPeriod(period);
            SessionGlobalData.getSessionGlobalData().setCurrentTutorship(tutorship);
            switch (typeUser) {
                case 2:
                    WindowManagement.changeScene("Menu de administrador", getClass().getResource("AdminMenu.fxml"));
                    break;
                case 1:
                    WindowManagement.changeScene("Menu principal", getClass().getResource("MainMenu.fxml"));
                    break;
            }
        } catch (IOException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
            WindowManagement.showAlert("Error", "Error no se pudo cargar el menu", Alert.AlertType.INFORMATION);
        } catch (SQLException exception) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, exception);
            WindowManagement.showAlert("Error", "Error al conectar con la base de datos, compruebe su conexion", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void signUp() {
        String uvAcount = txtUser.getText().trim().replaceAll(" ","");
        String password = txtPassword.getText();
        if(uvAcount.isEmpty() || uvAcount.length() > 50){
            lblInvalidUser.setVisible(true);
        }else if(password.isEmpty() || password.length() > 50){
            lblInvalidPassword.setVisible(true);
        }else {
            lblInvalidPassword.setVisible(false);
            lblInvalidUser.setVisible(false);
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
        URL linkImgUsers = getClass().getResource("/com/teamfour/sistutorias/images/users.png");
        Image intSignIn = new Image(linkImgUsers.toString(),15,15,false,true);
        btnSignIn.setGraphic(new ImageView(intSignIn));

        URL linkImgOut = getClass().getResource("/com/teamfour/sistutorias/images/exit.png");
        Image imgOut = new Image(linkImgOut.toString(),15,15,false,true);
        btnExit.setGraphic(new ImageView(imgOut));
    }
}
