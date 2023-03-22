package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.domain.Period;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class CounselorModificationController implements Initializable {

    public ListView TutorsList;
    public TextField tbTutorsName;
    public TextField tbPaternalSurname;
    public TextField tbMaternalSurname;
    public TextField tfTutors;
    @FXML
    public void populateList(){

        ListView<UserRoleProgram> listView = new ListView<>();
        UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
        ObservableList<UserRoleProgram> tutors = FXCollections.observableArrayList();

        try{
            tutors.addAll(userRoleProgramDAO.getTutors());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        if(!tfTutors.getText().isEmpty()){
            String tutorSearch = tfTutors.getText();
            for(UserRoleProgram tutor : tutors){
                if(tutor.getName().contains(tutorSearch) || tutor.getPaternalSurname().contains(tutorSearch) ||
                        tutor.getMaternalSurname().contains(tutorSearch)){
                    listView.getItems().add(tutor);
                }
            }
        } else {
            listView.setItems(tutors);
        }

        TutorsList.setItems(listView.getItems());
        this.TutorsList.setCellFactory(celldata -> {
            ListCell<UserRoleProgram> cell = new ListCell<UserRoleProgram>() {
                @Override
                //Hace que se muestre el nombre completo del tutor en la lista, haciendole override al método que
                //se encarga de mostrar el texto en la lista, para que en lugar de mostrar el texto de toString()
                //muestre el nombre completo del tutor
                protected void updateItem(UserRoleProgram item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getName() + " " + item.getPaternalSurname() + " " + item.getMaternalSurname());
                    }
                }
            };
            return cell;
        });
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

    public void modifyAction(ActionEvent actionEvent) {
        UserRoleProgram tutor = (UserRoleProgram) TutorsList.getSelectionModel().getSelectedItem();
        if(tutor != null){
            if(!tbTutorsName.getText().isEmpty() && !tbPaternalSurname.getText().isEmpty() && !tbMaternalSurname.getText().isEmpty()){
                tutor.setName(tbTutorsName.getText());
                tutor.setPaternalSurname(tbPaternalSurname.getText());
                tutor.setMaternalSurname(tbMaternalSurname.getText());
                UserRoleProgramDAO userRoleProgramDAO = new UserRoleProgramDAO();
                try {
                    userRoleProgramDAO.modifyCounselor(tutor);
                    JOptionPane.showMessageDialog(null, "Tutor modificado correctamente");
                    WindowManagement.closeWindow(actionEvent);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else{
                JOptionPane.showMessageDialog(null, "No se han llenado todos los campos");
            }
        } else{
            JOptionPane.showMessageDialog(null, "No se ha seleccionado un tutor");
        }
    }
}
