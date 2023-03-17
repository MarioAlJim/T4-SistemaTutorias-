package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.UserRoleProgramDAO;
import com.teamfour.sistutorias.domain.UserRoleProgram;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

public class TutorAssignmentController implements Initializable {

    @FXML
    private TextField tfTutorado;

    private ArrayList<UserRoleProgram> tutors = new ArrayList<>();
    @FXML
    private TextField tfTutor;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private String[] _possibleSuggestions;
    private Set<String> possibleSuggestions;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            prepareAutoCompletionTextFields();
        } catch (SQLException sqlException) {
            WindowManagement.connectionLostMessage();
        }
    }

    private void prepareAutoCompletionTextFields() throws SQLException {
        UserRoleProgramDAO tutorsDAO = new UserRoleProgramDAO();
        this.tutors = tutorsDAO.getTutors();
        this._possibleSuggestions = new String[this.tutors.size()];
        int iterator = 0;
        for(UserRoleProgram tutorUV : this.tutors) {
            this._possibleSuggestions[iterator] = tutorUV.getFullName();
            iterator++;
        }

        this.possibleSuggestions = new HashSet<>(Arrays.asList(_possibleSuggestions));
        //THROWS EXCEPTION (FIX)
        //TextFields.bindAutoCompletion(this.tfTutor, this._possibleSuggestions);
    }

    @FXML
    public void clickCancel(ActionEvent event) {
        WindowManagement.closeWindow(event);
    }

    @FXML
    public void clickSave(ActionEvent event) {

    }
}
