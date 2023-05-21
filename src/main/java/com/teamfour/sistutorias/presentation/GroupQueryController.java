package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.GroupDAO;
import com.teamfour.sistutorias.domain.Group;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GroupQueryController implements Initializable {

    @FXML
    private Label lbEducationProgram;
    @FXML
    private javafx.scene.control.Button btClose;
    @FXML
    private TableView<Group> tvAcademicOffer;

    @FXML
    private TableColumn<Group, String> tcNrc;

    @FXML
    private TableColumn<Group, String> tcName;

    @FXML
    private TableColumn<Group, String> tcTeacher;

    private ObservableList<Group> ofertasData;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seteETb();
    }
    private void setLabel() {
        lbEducationProgram.setText(SessionGlobalData.getSessionGlobalData().getUserRoleProgram().getEducativeProgram());
    }
    private void seteETb() {
        tcName.setCellValueFactory(new PropertyValueFactory<>("experience"));
        tcTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        tcNrc.setCellValueFactory(new PropertyValueFactory<>("nrc"));
        GroupDAO groupDAO = new GroupDAO();
        ArrayList<Group> groups;
        ofertasData = FXCollections.observableArrayList();
        try {
            groups= groupDAO.getGroupsList(SessionGlobalData.getSessionGlobalData().getActiveRole().getEducationProgram().getIdEducativeProgram(),
                    SessionGlobalData.getSessionGlobalData().getCurrentPeriod().getIdPeriod());
            ofertasData.addAll(groups);
        } catch (SQLException exception) {
            WindowManagement.showAlert("Error", "Error en la conexion con la base de datos", Alert.AlertType.INFORMATION);
            Logger.getLogger(ConsultAcademicProblemsController.class.getName()).log(Level.SEVERE, null, exception);
        }
        tvAcademicOffer.setItems(ofertasData);
    }


    public void closeAction(ActionEvent actionEvent) {

    }


}
