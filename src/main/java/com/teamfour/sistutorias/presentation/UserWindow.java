package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EducationProgramDAO;
import com.teamfour.sistutorias.bussinesslogic.UserDAO;
import com.teamfour.sistutorias.bussinesslogic.UserRoleProgramDAO;
import com.teamfour.sistutorias.domain.EducativeProgram;
import com.teamfour.sistutorias.domain.RoleProgram;
import com.teamfour.sistutorias.domain.User;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserWindow implements Initializable {

    @FXML
    private ComboBox<String> cbEducativeProgram;

    @FXML
    private CheckBox chbAdmin;

    @FXML
    private CheckBox chbCareerManager;

    @FXML
    private CheckBox chbCoordinator;

    @FXML
    private CheckBox chbTutor;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfMaternalSurname;

    @FXML
    private TextField tfName;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField tfPaternalSurname;
    private User user;
    private boolean isEdit;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEducativePrograms();
    }

    private void loadEducativePrograms() {
        EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
        try {
            ObservableList<EducativeProgram> educativePrograms = FXCollections.observableArrayList(educationProgramDAO.getEducationPrograms());
            for (EducativeProgram educativeProgram : educativePrograms) {
                cbEducativeProgram.getItems().add(educativeProgram.getName());
            }
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "Error al cargar los programas educativos", Alert.AlertType.ERROR);
            closeWindow(null);
        }
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) tfEmail.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveChanges(ActionEvent event) {
        checkFields();
    }

    private void checkFields() {
        if (tfEmail.getText().isEmpty() || pfPassword.getText().isEmpty() || tfName.getText().isEmpty() || tfPaternalSurname.getText().isEmpty() || tfMaternalSurname.getText().isEmpty() || cbEducativeProgram.getValue() == null) {
            WindowManagement.showAlert("Error", "Por favor, llene todos los campos", Alert.AlertType.ERROR);
        } else {
            if (!tfEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                WindowManagement.showAlert("Error", "Por favor, ingrese un correo electrónico válido", Alert.AlertType.ERROR);
                return;
            }
            if (pfPassword.getText().length() < 8) {
                WindowManagement.showAlert("Error", "Por favor, ingrese una contraseña de al menos 8 caracteres", Alert.AlertType.ERROR);
                return;
            }
            if (chbAdmin.isSelected() || chbCareerManager.isSelected() || chbCoordinator.isSelected() || chbTutor.isSelected()) {
                if (isEdit) {
                    modifyUser();
                } else {
                    saveUser();
                }
            } else {
                WindowManagement.showAlert("Error", "Por favor, seleccione al menos un rol", Alert.AlertType.ERROR);
            }
        }
    }

    private void saveUser() {
        user.setName(tfName.getText());
        user.setPaternalSurname(tfPaternalSurname.getText());
        user.setMaternalSurname(tfMaternalSurname.getText());
        user.setEmail(tfEmail.getText());
        user.setPassword(pfPassword.getText());
        user.setEducativeProgram(cbEducativeProgram.getValue());
        String roles = "";
        if (chbAdmin.isSelected()) {
            roles += "Administrador, ";
        } else {
            user.removeRole("Administrador");
        }
        if (chbCareerManager.isSelected()) {
            roles += "Jefe de carrera, ";
        } else {
            user.removeRole("Jefe de carrera");
        }
        if (chbCoordinator.isSelected()) {
            roles += "Coordinador, ";
        } else {
            user.removeRole("Coordinador");
        }
        if (chbTutor.isSelected()) {
            roles += "Tutor académico, ";
        } else {
            user.removeRole("Tutor académico");
        }
        if (roles.length() > 0) {
            roles = roles.substring(0, roles.length() - 2);
        }
        user.setRoles(roles);

        UserDAO userDAO = new UserDAO();
        try {
            int personId = userDAO.insertUser(user);
            if (personId != -1) {
                user.setIdPerson(personId);
                UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
                UserRoleProgram userRoleProgram = new UserRoleProgram();
                userRoleProgram.setIdPerson(user.getIdPerson());
                userRoleProgram.setEmail(user.getEmail());
                RoleProgram roleProgram = new RoleProgram();

                if (chbTutor.isSelected()) {
                    roleProgram.setRole(1);
                    roleProgram.getEducationProgram().setIdEducationProgram(1);
                    userRoleProgram.addRole(roleProgram);
                }
                if (chbCoordinator.isSelected()) {
                    roleProgram.setRole(2);
                    roleProgram.getEducationProgram().setIdEducationProgram(1);
                    userRoleProgram.addRole(roleProgram);
                }
                if (chbCareerManager.isSelected()) {
                    roleProgram.setRole(3);
                    roleProgram.getEducationProgram().setIdEducationProgram(1);
                    userRoleProgram.addRole(roleProgram);
                }
                if (chbAdmin.isSelected()) {
                    roleProgram.setRole(4);
                    roleProgram.getEducationProgram().setIdEducationProgram(1);
                    userRoleProgram.addRole(roleProgram);
                }
                userRoleProgramDAO.insertRoleProgram(userRoleProgram);
            }
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "Error al guardar el usuario", Alert.AlertType.ERROR);
            e.printStackTrace();
            return;
        }

        System.out.println(user);
        WindowManagement.showAlert("Éxito", "Usuario guardado con éxito", Alert.AlertType.INFORMATION);
        registerAnotherUserDialog();
    }

    private void modifyUser() {
        user.setName(tfName.getText());
        user.setPaternalSurname(tfPaternalSurname.getText());
        user.setMaternalSurname(tfMaternalSurname.getText());
        user.setEmail(tfEmail.getText());
        user.setPassword(pfPassword.getText());
        user.setEducativeProgram(cbEducativeProgram.getValue());
        user.setRoles("");
        String roles = "";
        if (chbAdmin.isSelected()) {
            roles += "Administrador, ";
        } else {
            user.removeRole("Administrador");
        }
        if (chbCareerManager.isSelected()) {
            roles += "Jefe de carrera, ";
        } else {
            user.removeRole("Jefe de carrera");
        }
        if (chbCoordinator.isSelected()) {
            roles += "Coordinador, ";
        } else {
            user.removeRole("Coordinador");
        }
        if (chbTutor.isSelected()) {
            roles += "Tutor académico, ";
        } else {
            user.removeRole("Tutor académico");
        }
        if (roles.length() > 0) {
            roles = roles.substring(0, roles.length() - 2);
        }
        user.setRoles(roles);

        UserDAO userDAO = new UserDAO();
        UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
        try {
            if(userDAO.updateUser(user) != 1) {
                WindowManagement.showAlert("Error", "Error al modificar el usuario 1", Alert.AlertType.ERROR);
            }
            if(userRoleProgramDAO.deleteRoleProgram(user.getEmail()) < 1) {
                WindowManagement.showAlert("Error", "Error al modificar el usuario 2", Alert.AlertType.ERROR);
            }
            UserRoleProgram userRoleProgram = new UserRoleProgram();
            userRoleProgram.setIdPerson(user.getIdPerson());
            userRoleProgram.setEmail(user.getEmail());
            RoleProgram roleProgram = new RoleProgram();

            if (chbTutor.isSelected()) {
                roleProgram.setRole(1);
                roleProgram.getEducationProgram().setIdEducationProgram(1);
                userRoleProgram.addRole(roleProgram);
            }
            if (chbCoordinator.isSelected()) {
                roleProgram.setRole(2);
                roleProgram.getEducationProgram().setIdEducationProgram(1);
                userRoleProgram.addRole(roleProgram);
            }
            if (chbCareerManager.isSelected()) {
                roleProgram.setRole(3);
                roleProgram.getEducationProgram().setIdEducationProgram(1);
                userRoleProgram.addRole(roleProgram);
            }
            if (chbAdmin.isSelected()) {
                roleProgram.setRole(4);
                roleProgram.getEducationProgram().setIdEducationProgram(1);
                userRoleProgram.addRole(roleProgram);
            }
            userRoleProgramDAO.insertRoleProgram(userRoleProgram);
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "Error al modificar el usuario", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        WindowManagement.showAlert("Éxito", "Usuario modificado con éxito", Alert.AlertType.INFORMATION);
        closeWindow(null);
    }

    private void registerAnotherUserDialog() {
        if (WindowManagement.showAlertWithConfirmation("Registro de usuario", "¿Desea registrar otro usuario?")) {
            clearFields();
        } else {
            closeWindow(null);
        }
    }

    private void clearFields() {
        tfEmail.clear();
        tfName.clear();
        tfPaternalSurname.clear();
        tfMaternalSurname.clear();
        pfPassword.clear();
        cbEducativeProgram.setValue(null);
        chbAdmin.setSelected(false);
        chbCareerManager.setSelected(false);
        chbCoordinator.setSelected(false);
        chbTutor.setSelected(false);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void loadUser() {
        if (isEdit && user != null && user.getEmail() != null) {
            tfEmail.setText(user.getEmail());
            tfEmail.setDisable(true);
            tfName.setText(user.getName());
            tfPaternalSurname.setText(user.getPaternalSurname());
            tfMaternalSurname.setText(user.getMaternalSurname());
            cbEducativeProgram.setValue(user.getEducativeProgram());
            String[] roles = user.getRoles().split(", ");
            for (String role : roles) {
                if (role.equalsIgnoreCase("administrador")) {
                    chbAdmin.setSelected(true);
                } else if (role.equalsIgnoreCase("jefe de carrera")) {
                    chbCareerManager.setSelected(true);
                } else if (role.equalsIgnoreCase("coordinador")) {
                    chbCoordinator.setSelected(true);
                } else if (role.equalsIgnoreCase("tutor académico") || role.equalsIgnoreCase("tutor academico") || role.equalsIgnoreCase("tutor")) {
                    chbTutor.setSelected(true);
                }
            }
        } else {
            user = new User();
        }
    }
}
