package com.teamfour.sistutorias.presentation;

import com.teamfour.sistutorias.bussinesslogic.PeriodDAO;
import com.teamfour.sistutorias.bussinesslogic.TeacherDAO;
import com.teamfour.sistutorias.domain.Teacher;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.sql.Date;
import java.time.DayOfWeek;
import javafx.util.StringConverter;
import com.teamfour.sistutorias.bussinesslogic.TutorshipDAO;
import com.teamfour.sistutorias.domain.Tutorship;
import com.teamfour.sistutorias.domain.Period;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class TutorshipController implements Initializable {
    @javafx.fxml.FXML
    private Button cancelButton;
    @javafx.fxml.FXML
    private Button addButton;
    @javafx.fxml.FXML
    private Label periodLabel;
    @javafx.fxml.FXML
    private Label firstSessionLabel;
    @javafx.fxml.FXML
    private Label endFirstSessionLabel;
    @javafx.fxml.FXML
    private Label instructionsLabel;
    @javafx.fxml.FXML
    private DatePicker firstDatePicker;
    @javafx.fxml.FXML
    private DatePicker endFirstDatePicker;
    @javafx.fxml.FXML
    private Label secondSessionLabel;
    @javafx.fxml.FXML
    private Label endSecondSessionLabel;
    @javafx.fxml.FXML
    private DatePicker secondDatePicker;
    @javafx.fxml.FXML
    private DatePicker endSecondDatePicker;
    @javafx.fxml.FXML
    private Label thirdSessionLabel;
    @javafx.fxml.FXML
    private Label endThirdSessionLabel;
    @javafx.fxml.FXML
    private DatePicker thirdDatePicker;
    @javafx.fxml.FXML
    private DatePicker endThirdDatePicker;
    @javafx.fxml.FXML
    private ComboBox cbPeriod;

    @javafx.fxml.FXML
    public void cancelAction(ActionEvent actionEvent) {
        WindowManagement.closeWindow(actionEvent);
    }


    @javafx.fxml.FXML
    public void addAction(ActionEvent actionEvent) throws SQLException {

        TutorshipDAO tutorshipDAO = new TutorshipDAO();

        if (firstDatePicker.getValue() != null && endFirstDatePicker.getValue() != null &&
                secondDatePicker.getValue() != null && endSecondDatePicker.getValue() != null &&
                thirdDatePicker.getValue() != null && endThirdDatePicker.getValue() != null) {
            Tutorship tutorship = new Tutorship();
            Period period = (Period) cbPeriod.getSelectionModel().getSelectedItem();
            period.getIdPeriod();
            tutorship.setPeriodId(period.getIdPeriod());
            tutorship.setStart(Date.valueOf(firstDatePicker.getValue()));
            tutorship.setEnd(Date.valueOf(endFirstDatePicker.getValue());
            tutorshipDAO.addTutorship(tutorship);
            Tutorship tutorship1 = new Tutorship();
            Period period1 = (Period) cbPeriod.getSelectionModel().getSelectedItem();
            period1.getIdPeriod();
            tutorship1.setPeriodId(period1.getIdPeriod());
            tutorship1.setStart(Date.valueOf(secondDatePicker.getValue()));
            tutorship1.setEnd(Date.valueOf(endSecondDatePicker.getValue()));
            tutorshipDAO.addTutorship(tutorship1);

            Tutorship tutorship2 = new Tutorship();
            Period period2 = (Period) cbPeriod.getSelectionModel().getSelectedItem();
            period2.getIdPeriod();
            tutorship2.setPeriodId(period2.getIdPeriod());
            tutorship2.setStart(Date.valueOf(thirdDatePicker.getValue()));
            tutorship2.setEnd(Date.valueOf(endThirdDatePicker.getValue()));
            tutorshipDAO.addTutorship(tutorship2);
            if (tutorshipDAO.addTutorship(tutorship) && tutorshipDAO.addTutorship(tutorship1) && tutorshipDAO.addTutorship(tutorship2)) {
                WindowManagement.showAlert("Sesiones de tutoría agregadas",
                        "Las sesiones de tutoría se han agregado correctamente",
                        Alert.AlertType.INFORMATION);

            } else {
                WindowManagement.showAlert("Sesiones de tutoría no fueron agregadas",
                        "Las sesiones de tutoría no se han agregado",
                        Alert.AlertType.INFORMATION);
            }
        }else {
            WindowManagement.showAlert("Campos invalidos",
                    "Por favor, ingrese datos validos",
                    Alert.AlertType.INFORMATION);
        }
    }

    public void populateComboBox() throws SQLException {
        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<Period> periods = FXCollections.observableArrayList();
        periods.addAll(periodDAO.getPeriods());
        this.cbPeriod.setItems(periods);
        this.cbPeriod.getSelectionModel().selectFirst();
        this.cbPeriod.setConverter(new StringConverter<Period>() {
        @Override
        public String toString(Period period) {
            return period == null ? null : period.getStart() + " - " + period.getEnd();
        }
        @Override
        public Period fromString(String s) {
            return null;
        }
    });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            populateComboBox();
            TutorshipDAO tutorship = new TutorshipDAO();
            List<Tutorship> tutorships = tutorship.getTutorship(1);
            for (Tutorship tutorship2 : tutorships) {
                System.out.println(tutorship2.getPeriodId());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Callback<DatePicker, DateCell> dayCellFactory = (DatePicker picker) -> new DateCell() {
            @Override
            public void updateItem(LocalDate localDate, boolean isEmpty) {
                super.updateItem(localDate, isEmpty);
                if(isEmpty || localDate.getDayOfWeek() == DayOfWeek.SATURDAY || localDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    setDisable(true);
                }
            }
        };
        firstDatePicker.setDayCellFactory(dayCellFactory);
        endFirstDatePicker.setDayCellFactory(dayCellFactory);
        secondDatePicker.setDayCellFactory(dayCellFactory);
        endSecondDatePicker.setDayCellFactory(dayCellFactory);
        thirdDatePicker.setDayCellFactory(dayCellFactory);
        endThirdDatePicker.setDayCellFactory(dayCellFactory);
    }

    public void dateFormatter() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = firstDatePicker.getValue().format(formatter);
        String date2 = endFirstDatePicker.getValue().format(formatter);
        String date3 = secondDatePicker.getValue().format(formatter);
        String date4 = endSecondDatePicker.getValue().format(formatter);
        String date5 = thirdDatePicker.getValue().format(formatter);
        String date6 = endThirdDatePicker.getValue().format(formatter);
    }
}
