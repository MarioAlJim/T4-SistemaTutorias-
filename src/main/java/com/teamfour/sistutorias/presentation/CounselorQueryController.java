package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.domain.Period;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import com.teamfour.sistutorias.bussinesslogic.UserRoleProgramDAO;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CounselorQueryController implements Initializable {

    public ListView TutorsList;
    public TextField tbTutorsName;
    public TextField tbPaternalSurname;
    public TextField tbMaternalSurname;
    public TextField tfTutors;

    public void populateList(){

        ListView<UserRoleProgram> listView = new ListView<>();
        UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
        ObservableList<UserRoleProgram> tutors = FXCollections.observableArrayList();

        FilteredList<UserRoleProgram> filteredData = new FilteredList<>(tutors, b -> true);
        tfTutors.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(userRoleProgram -> {
                if (newValue == null || newValue.isEmpty()) {
                    populateList();
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (userRoleProgram.getFullName().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true;
                }else {
                    return false;
                }
            });
        });
        SortedList<UserRoleProgram> sortedData = new SortedList<>(filteredData);
        listView.setItems(sortedData);


        try{
            tutors.addAll(userRoleProgramDAO.getTutors());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        listView.setItems(tutors);
        TutorsList.setItems(listView.getItems());
        this.TutorsList.setCellFactory(new Callback<ListView<UserRoleProgram>, ListCell<UserRoleProgram>>() {
            @Override
            public ListCell<UserRoleProgram> call(ListView<UserRoleProgram> userRoleProgramListView) {
                return new ListCell<UserRoleProgram>() {
                    @Override
                    protected void updateItem(UserRoleProgram userRoleProgram, boolean b) {
                        super.updateItem(userRoleProgram, b);
                        if (userRoleProgram != null) {
                            setText(userRoleProgram.getFullName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        }
      );
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            populateList();
    }


    public void cancelAction(ActionEvent actionEvent) {
        WindowManagement.closeWindow(actionEvent);
    }

    public void clickedTutorList(MouseEvent mouseEvent) {
        if(mouseEvent.getClickCount() == 2){
            UserRoleProgram tutor = (UserRoleProgram) TutorsList.getSelectionModel().getSelectedItem();
            if(tutor != null){
                tbTutorsName.setText(tutor.getName());
                tbPaternalSurname.setText(tutor.getPaternalSurname());
                tbMaternalSurname.setText(tutor.getMaternalSurname());
            } else{
                JOptionPane.showMessageDialog(null, "No se ha seleccionado un tutor");
            }
        }
    }
}
