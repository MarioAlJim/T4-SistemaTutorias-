package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EducationProgramDAO;
import com.teamfour.sistutorias.bussinesslogic.PersonDAO;
import com.teamfour.sistutorias.bussinesslogic.UserDAO;
import com.teamfour.sistutorias.bussinesslogic.UserRoleProgramDAO;
import com.teamfour.sistutorias.domain.EducationProgram;
import com.teamfour.sistutorias.domain.Person;
import com.teamfour.sistutorias.domain.User;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserWindow implements Initializable {

    private final HashMap<String, Integer> ROLE_MAP = new HashMap<String, Integer>() {{
        put("Tutor Académico", 1);
        put("Coordinador", 2);
        put("Jefe de Carrera", 3);
        put("Administrador", 4);
    }};

    private final HashMap<String, Integer> EDUCATIVE_PROGRAM_MAP = new HashMap<String, Integer>() {{
        put("Ingeniería de Software", 1);
    }};

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
    private TextField tfPassword;

    @FXML
    private TextField tfPaternalSurname;
    private User user;
    private boolean isEdit;
    private ObservableList<EducationProgram> educationPrograms;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadEducativePrograms();
    }

    private void loadEducativePrograms() {
        EducationProgramDAO educationProgramDAO = new EducationProgramDAO();
        try {
            educationPrograms = FXCollections.observableArrayList(educationProgramDAO.getEducationPrograms());
            for (EducationProgram educationProgram : educationPrograms) {
                cbEducativeProgram.getItems().add(educationProgram.getName());
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
        if (tfEmail.getText().isEmpty() || tfPassword.getText().isEmpty() || tfName.getText().isEmpty() || tfPaternalSurname.getText().isEmpty() || tfMaternalSurname.getText().isEmpty() || cbEducativeProgram.getValue() == null) {
            WindowManagement.showAlert("Error", "Por favor, llene todos los campos", Alert.AlertType.ERROR);
        } else {
            if (!tfEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                WindowManagement.showAlert("Error", "Por favor, ingrese un correo electrónico válido", Alert.AlertType.ERROR);
                return;
            }
            if (tfPassword.getText().length() < 8) {
                WindowManagement.showAlert("Error", "Por favor, ingrese una contraseña de al menos 8 caracteres", Alert.AlertType.ERROR);
                return;
            }
            if (chbAdmin.isSelected() || chbCareerManager.isSelected() || chbCoordinator.isSelected() || chbTutor.isSelected()) {
                saveUser();
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
        user.setPassword(tfPassword.getText());
        user.setEducativeProgram(cbEducativeProgram.getValue());
        String roles = "";
        if (chbAdmin.isSelected()) {
            roles += "Administrador, ";
        }
        if (chbCareerManager.isSelected()) {
            roles += "Jefe de carrera, ";
        }
        if (chbCoordinator.isSelected()) {
            roles += "Coordinador, ";
        }
        if (chbTutor.isSelected()) {
            roles += "Tutor académico, ";
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
                if (chbAdmin.isSelected()) {
                    userRoleProgram.setIdRole(ROLE_MAP.get("Administrador"));
                    userRoleProgram.setIdProgram(EDUCATIVE_PROGRAM_MAP.get(cbEducativeProgram.getValue()));
                    userRoleProgramDAO.insertRoleProgram(userRoleProgram);
                }
                if (chbCareerManager.isSelected()) {
                    userRoleProgram.setIdRole(ROLE_MAP.get("Jefe de Carrera"));
                    userRoleProgram.setIdProgram(EDUCATIVE_PROGRAM_MAP.get(cbEducativeProgram.getValue()));
                    userRoleProgramDAO.insertRoleProgram(userRoleProgram);
                }
                if (chbCoordinator.isSelected()) {
                    userRoleProgram.setIdRole(ROLE_MAP.get("Coordinador"));
                    userRoleProgram.setIdProgram(EDUCATIVE_PROGRAM_MAP.get(cbEducativeProgram.getValue()));
                    userRoleProgramDAO.insertRoleProgram(userRoleProgram);
                }
                if (chbTutor.isSelected()) {
                    userRoleProgram.setIdRole(ROLE_MAP.get("Tutor Académico"));
                    userRoleProgram.setIdProgram(EDUCATIVE_PROGRAM_MAP.get(cbEducativeProgram.getValue()));
                    userRoleProgramDAO.insertRoleProgram(userRoleProgram);
                }
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
        tfPassword.clear();
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
                } else if (role.equalsIgnoreCase("tutor académico")) {
                    chbTutor.setSelected(true);
                }
            }
        } else {
            user = new User();
        }
    }
}
