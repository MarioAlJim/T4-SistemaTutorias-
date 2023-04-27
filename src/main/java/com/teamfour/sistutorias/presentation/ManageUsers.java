package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.UserDAO;
import com.teamfour.sistutorias.domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    private TableColumn<?, ?> tcProgramaEducativo;

    @FXML
    private TextField tfUserSearch;

    @FXML
    private TableView<User> tvUsers;

    ObservableList<User> users;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        loadUsers();
        filterTable();
    }

    private void filterTable() {
        FilteredList<User> filteredUsers = new FilteredList<>(this.users, b -> true);
        this.tfUserSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredUsers.setPredicate(
                user -> {
                    if(newValue == null || newValue.isEmpty())
                        return true;

                    String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");
                    boolean isInTable = user.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter);
                    if(user.getPaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }
                    if(user.getMaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }
                    if(user.getEmail().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }
                    if(user.getRoles().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }
                    if(user.getEducativeProgram().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }

                    return isInTable;
                }
        ));
        SortedList<User> userSortedList = new SortedList<>(filteredUsers);
        userSortedList.comparatorProperty().bind(this.tvUsers.comparatorProperty());
        this.tvUsers.setItems(userSortedList);
    }

    private void setupTable() {
        tcNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        tcPaternalSurname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("paternalSurname"));
        tcMaternalSurname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("maternalSurname"));
        tcEmail.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("email"));
        tcRoles.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("roles"));
        tcProgramaEducativo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("educativeProgram"));
    }

    private void loadUsers() {
        UserDAO userDAO = new UserDAO();
        try {
            users = FXCollections.observableArrayList(userDAO.getAllUsers());
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "Error al cargar los usuarios", Alert.AlertType.ERROR);
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
        openUserWindow(tvUsers.getSelectionModel().getSelectedItem());
    }

    @FXML
    void openRegisterUserWindow(ActionEvent event) {
        openUserWindow(null);
    }

    private void openUserWindow(User selectedItem) {
        Stage stage = new Stage();
        stage.setTitle("Usuario");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserWindow.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            UserWindow userWindow = loader.getController();
            userWindow.setUser(selectedItem);
            userWindow.setEdit(selectedItem != null);
            userWindow.loadUser();
            stage.showAndWait();
            loadUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
