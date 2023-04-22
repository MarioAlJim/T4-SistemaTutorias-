package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.EEDAO;
import com.teamfour.sistutorias.domain.EE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import com.teamfour.sistutorias.presentation.DataValidation;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class EEAdministrationController implements Initializable {
    @FXML
    private TextField eeTf;
    @FXML
    private TableView<EE> eeTb;
    @FXML
    private TableColumn<EE, String> nameEETb;
    private ArrayList<EE> ees;
    @FXML
    public void eeTable(SortEvent<TableView> tableViewSortEvent) {

    }
    @FXML
    public void deleteEE(ActionEvent actionEvent) {
        EE ee = eeTb.getSelectionModel().getSelectedItem();
        if(ee == null) {
            WindowManagement.showAlert("No se ha seleccionado ninguna EE",
                    "Por favor, seleccione una EE",
                    Alert.AlertType.INFORMATION);
        } else {
            EEDAO eeDAO = new EEDAO();
            try {
                boolean confirm =
                WindowManagement.showAlertWithConfirmation("Confirmacion",
                        "¿Esta seguro que desea eliminar la EE?"
                );
                if(!confirm) return;
                eeDAO.delete(ee);
                loadTable();
                WindowManagement.showAlert("EE eliminada",
                        "La EE se ha eliminado correctamente",
                        Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void cancelAction(ActionEvent actionEvent) {
        WindowManagement.closeWindow(actionEvent);
    }
    @FXML
    public void addEE(ActionEvent actionEvent) {
        String name = eeTf.getText();
        if(name.isEmpty() || name.isBlank())
            WindowManagement.showAlert("Campos vacios",
                    "Por favor, no deje campos vacios",
                    Alert.AlertType.INFORMATION);
        if (DataValidation.textValidation(name, 50)) {
            boolean eeExists = false;
            for (EE ee : ees) {
                if (ee.getName().equals(name)) {
                    eeExists = true;
                    break;
                }
            }
            if (eeExists) {
                WindowManagement.showAlert("EE ya registrada",
                        "La EE ya se encuentra registrada",
                        Alert.AlertType.INFORMATION);
                return;
            }
            EE ee = new EE();
            ee.setName(name);
            EEDAO eeDAO = new EEDAO();
            try {
                eeDAO.register(ee);
                loadTable();
                WindowManagement.showAlert("EE registrada",
                        "La EE se ha registrado correctamente",
                        Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            WindowManagement.showAlert("Campos invalidos",
                    "Por favor, no ingrese caracteres especiales",
                    Alert.AlertType.INFORMATION);
        }
    }
    @FXML
    public void updateEE(ActionEvent actionEvent) {
        String name = eeTf.getText();
        if(name.isEmpty() || name.isBlank())
            WindowManagement.showAlert("Campos vacios",
                    "Por favor, no deje campos vacios",
                    Alert.AlertType.INFORMATION);
        if (DataValidation.textValidation(name, 50)) {
            boolean eeExists = false;
            for (EE ee : ees) {
                if (ee.getName().equals(name)) {
                    eeExists = true;
                    break;
                }
            }
            if (eeExists) {
                WindowManagement.showAlert("EE ya registrada",
                        "La EE ya se encuentra registrada",
                        Alert.AlertType.INFORMATION);
                return;
            }
            EE ee = eeTb.getSelectionModel().getSelectedItem();
            ee.setName(name);
            EEDAO eeDAO = new EEDAO();
            try {
                boolean result = eeDAO.update(ee);
                if(result) {
                    loadTable();
                    WindowManagement.showAlert("EE actualizada",
                            "La EE se ha actualizado correctamente",
                            Alert.AlertType.INFORMATION);
                } else {
                    WindowManagement.showAlert("EE no actualizada",
                            "La EE no se ha actualizado correctamente",
                            Alert.AlertType.INFORMATION);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            WindowManagement.showAlert("Campos invalidos",
                    "Por favor, no ingrese caracteres especiales",
                    Alert.AlertType.INFORMATION);
        }
    }

    public void setTableColumns(){
        nameEETb.setCellValueFactory(new PropertyValueFactory<EE, String>("name"));
    }

    public void loadTable(){
        EEDAO eeDAO = new EEDAO();
        try {
            eeTb.getItems().clear();
            ees = eeDAO.getEEs();
            eeTb.getItems().addAll(ees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTableColumns();
        loadTable();
    }
    @FXML
    public void selectEE(MouseEvent mouseEvent) {
        eeTf.setText(eeTb.getSelectionModel().getSelectedItem().getName());
    }
}
