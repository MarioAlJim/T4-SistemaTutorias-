package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.UserDAO;
import com.teamfour.sistutorias.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManageUsers implements Initializable {

    @FXML
    private TableColumn<?, ?> tcEmail;

    @FXML
    private TableColumn<?, ?> tcMaternalSurname;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcPaternalSurname;

    @FXML
    private TableColumn<?, ?> tcRoles;

    @FXML
    private TextField tfUserSearch;

    @FXML
    private TableView<User> tvUsers;

    ObservableList<User> users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        loadUsers();
        tfUserSearch.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> filterTable());
    }

    private void filterTable() {
        if (tfUserSearch.getText().equals("")) {
            tvUsers.setItems(users);
            return;
        }
        tvUsers.getItems().clear();
        for(User user : users) {
            if(user.getEmail().contains(tfUserSearch.getText())) {
                tvUsers.getItems().add(user);
            }
            if(user.getName().contains(tfUserSearch.getText())) {
                tvUsers.getItems().add(user);
            }
            if(user.getPaternalSurname().contains(tfUserSearch.getText())) {
                tvUsers.getItems().add(user);
            }
            if(user.getMaternalSurname().contains(tfUserSearch.getText())) {
                tvUsers.getItems().add(user);
            }
        }
    }

    private void setupTable() {
        tcNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        tcPaternalSurname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("paternalSurname"));
        tcMaternalSurname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("maternalSurname"));
        tcEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));
        tcRoles.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("roles"));
    }

    private void loadUsers() {
        UserDAO userDAO = new UserDAO();
        try {
            users = FXCollections.observableArrayList(userDAO.getAllUsers());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Error al cargar los usuarios").show();
            e.printStackTrace();
        }
        tvUsers.setItems(users);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) tfUserSearch.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openModifyUserWindow(ActionEvent event) {
        //TODO: Open modify user window
    }

    @FXML
    void openRegisterUserWindow(ActionEvent event) {
        //TODO: Open register user window
    }
}
