package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.TutoradoDAO;
import com.teamfour.sistutorias.domain.Tutorado;
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

public class ManageTutorados implements Initializable {

    @FXML
    private TableColumn<?, ?> tcMatricula;

    @FXML
    private TableColumn<?, ?> tcMaternalSurname;

    @FXML
    private TableColumn<?, ?> tcNombre;

    @FXML
    private TableColumn<?, ?> tcPaternalSurname;

    @FXML
    private TableColumn<?, ?> tcProgramaEducativo;

    @FXML
    private TextField tfTutoradoSearch;

    @FXML
    private TableView<Tutorado> tvTutorados;

    private ObservableList<Tutorado> tutorados;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        loadTutorados();
        filterTable();
    }

    private void filterTable() {
        FilteredList<Tutorado> filteredTutorados = new FilteredList<>(this.tutorados, b -> true);
        this.tfTutoradoSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredTutorados.setPredicate(
                tutorado -> {
                    if(newValue == null || newValue.isEmpty())
                        return true;

                    String lowerCaseFilter = newValue.toLowerCase().replaceAll("\\s", "");
                    boolean isInTable = tutorado.getName().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter);
                    if(tutorado.getPaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }
                    if(tutorado.getMaternalSurname().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }
                    /*if(tutorado.getEducativeProgram().toLowerCase().replaceAll("\\s", "").contains(lowerCaseFilter)) {
                        isInTable = true;
                    }*/

                    return isInTable;
                }
        ));
        SortedList<Tutorado> tutoradoSortedList = new SortedList<>(filteredTutorados);
        tutoradoSortedList.comparatorProperty().bind(this.tvTutorados.comparatorProperty());
        this.tvTutorados.setItems(tutoradoSortedList);
    }

    private void setupTable() {
        tcMatricula.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("registrationNumber"));
        tcNombre.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("name"));
        tcPaternalSurname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("paternalSurname"));
        tcMaternalSurname.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("maternalSurname"));
        tcProgramaEducativo.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("educativeProgram"));
    }

    private void loadTutorados() {
        TutoradoDAO tutoradoDAO = new TutoradoDAO();
        try {
            tutorados = FXCollections.observableArrayList(tutoradoDAO.getAll());
        } catch (SQLException e) {
            WindowManagement.showAlert("Error", "Error al cargar los tutorados.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        tvTutorados.setItems(tutorados);
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) tfTutoradoSearch.getScene().getWindow();
        stage.close();
    }

    @FXML
    void openModifyUserWindow(ActionEvent event) {
        openTutoradoWindow(tvTutorados.getSelectionModel().getSelectedItem());
    }

    @FXML
    void openRegisterUserWindow(ActionEvent event) {
        openTutoradoWindow(null);
    }

    private void openTutoradoWindow(Tutorado selectedItem) {
        Stage stage = new Stage();
        stage.setTitle("Tutorado");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TutoradoWindow.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            TutoradoWindow tutoradoWindow = loader.getController();
            tutoradoWindow.setTutorado(selectedItem);
            tutoradoWindow.setEdit(selectedItem != null);
            tutoradoWindow.loadTutorado();
            stage.showAndWait();
            loadTutorados();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
